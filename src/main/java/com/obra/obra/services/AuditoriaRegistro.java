package com.obra.obra.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obra.obra.entities.*;
import com.obra.obra.repositories.EventoAuditoriaRepository;
import com.obra.obra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Grava eventos de auditoria numa transacao separada (REQUIRES_NEW),
 * porque e chamado de dentro do flush do Hibernate.
 */
@Service
@RequiredArgsConstructor
public class AuditoriaRegistro {

    private final EventoAuditoriaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public static final Map<Class<?>, String> CHAVE_POR_CLASSE = Map.of(
            Gasto.class, "gasto",
            ItemCompra.class, "item_compra",
            Cotacao.class, "cotacao",
            Fornecedor.class, "fornecedor",
            Comprovante.class, "comprovante",
            OrcamentoConfig.class, "orcamento_config"
    );

    private static final Map<String, String> ARTIGO_ROTULO = Map.of(
            "gasto", "o gasto",
            "item_compra", "o item da lista de compras",
            "cotacao", "a cotação",
            "fornecedor", "o fornecedor",
            "comprovante", "o comprovante",
            "orcamento_config", "a configuração de orçamento"
    );

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrar(AcaoAuditoria acao, Object entidadeObj, Object id,
                          Map<String, Object> antes, Map<String, Object> depois) {
        String chave = CHAVE_POR_CLASSE.get(entidadeObj.getClass());
        if (chave == null) {
            return;
        }

        EventoAuditoria evento = new EventoAuditoria();
        preencherUsuario(evento);
        evento.setEntidade(chave);
        evento.setEntidadeId(String.valueOf(id));
        evento.setEntidadeDescricao(descricaoDaEntidade(entidadeObj));
        evento.setAcao(acao);
        evento.setDescricao(montarDescricao(acao, chave, evento.getEntidadeDescricao()));
        evento.setDadosAnteriores(paraJson(antes));
        evento.setDadosNovos(paraJson(depois));
        evento.setCriadoEm(LocalDateTime.now());

        repository.save(evento);
    }

    private void preencherUsuario(EventoAuditoria evento) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (auth != null && auth.isAuthenticated()) ? auth.getName() : null;

        if (email != null) {
            usuarioRepository.findByEmail(email).ifPresentOrElse(u -> {
                evento.setUsuarioId(u.getId());
                evento.setUsuarioNome(u.getNome());
            }, () -> evento.setUsuarioNome(email));
        } else {
            evento.setUsuarioNome("sistema");
        }
    }

    private String descricaoDaEntidade(Object entidade) {
        if (entidade instanceof Gasto g) return g.getDescricao();
        if (entidade instanceof ItemCompra i) return i.getDescricao();
        if (entidade instanceof Cotacao c) return c.getItem();
        if (entidade instanceof Fornecedor f) return f.getNome();
        if (entidade instanceof Comprovante c) return c.getNomeArquivo();
        if (entidade instanceof OrcamentoConfig) return "Configuração de orçamento";
        return null;
    }

    private String montarDescricao(AcaoAuditoria acao, String chave, String descEntidade) {
        String verbo = switch (acao) {
            case CRIACAO -> "Criou";
            case EDICAO -> "Atualizou";
            case EXCLUSAO -> "Excluiu";
        };
        String rotulo = ARTIGO_ROTULO.getOrDefault(chave, chave);
        return descEntidade != null
                ? "%s %s \"%s\"".formatted(verbo, rotulo, descEntidade)
                : "%s %s".formatted(verbo, rotulo);
    }

    private String paraJson(Map<String, Object> dados) {
        if (dados == null || dados.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(dados);
        } catch (Exception e) {
            return null;
        }
    }
}
