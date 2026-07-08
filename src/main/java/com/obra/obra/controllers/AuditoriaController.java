package com.obra.obra.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obra.obra.entities.AcaoAuditoria;
import com.obra.obra.entities.EventoAuditoria;
import com.obra.obra.repositories.EventoAuditoriaRepository;
import com.obra.obra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final EventoAuditoriaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public record EventoDTO(String id, String usuarioId, String usuarioNome,
                            String entidade, String entidadeId, String entidadeDescricao,
                            String acao, String descricao,
                            Map<String, Object> dadosAnteriores, Map<String, Object> dadosNovos,
                            String criadoEm) {}

    public record RespostaPaginadaDTO(List<EventoDTO> itens, long total, int pagina, int tamanhoPagina) {}

    public record UsuarioResumoDTO(String id, String nome) {}

    public record EntidadeResumoDTO(String chave, String rotulo) {}

    private static final List<EntidadeResumoDTO> ENTIDADES = List.of(
            new EntidadeResumoDTO("gasto", "Gastos"),
            new EntidadeResumoDTO("item_compra", "Lista de compras"),
            new EntidadeResumoDTO("cotacao", "Orçamentos/Cotações"),
            new EntidadeResumoDTO("fornecedor", "Fornecedores"),
            new EntidadeResumoDTO("comprovante", "Comprovantes"),
            new EntidadeResumoDTO("orcamento_config", "Configuração financeira")
    );

    @GetMapping
    public RespostaPaginadaDTO listar(@RequestParam(defaultValue = "1") int pagina,
                                      @RequestParam(defaultValue = "10") int tamanhoPagina,
                                      @RequestParam(required = false) Long usuarioId,
                                      @RequestParam(required = false) String entidade,
                                      @RequestParam(required = false) AcaoAuditoria acao,
                                      @RequestParam(required = false) String dataInicio,
                                      @RequestParam(required = false) String dataFim,
                                      @RequestParam(required = false) String busca) {

        LocalDateTime inicio = dataInicio == null || dataInicio.isBlank()
                ? null : LocalDate.parse(dataInicio).atStartOfDay();
        LocalDateTime fim = dataFim == null || dataFim.isBlank()
                ? null : LocalDate.parse(dataFim).atTime(23, 59, 59);
        String termo = busca == null || busca.isBlank() ? null : busca.trim();
        String chaveEntidade = entidade == null || entidade.isBlank() ? null : entidade;

        Page<EventoAuditoria> page = repository.buscar(
                usuarioId, chaveEntidade, acao, inicio, fim, termo,
                PageRequest.of(Math.max(0, pagina - 1), tamanhoPagina, Sort.by(Sort.Direction.DESC, "criadoEm")));

        return new RespostaPaginadaDTO(
                page.getContent().stream().map(this::toDTO).toList(),
                page.getTotalElements(), pagina, tamanhoPagina);
    }

    @GetMapping("/usuarios")
    public List<UsuarioResumoDTO> usuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResumoDTO(String.valueOf(u.getId()), u.getNome()))
                .toList();
    }

    @GetMapping("/entidades")
    public List<EntidadeResumoDTO> entidades() {
        return ENTIDADES;
    }

    @GetMapping("/{entidade}/{entidadeId}")
    public List<EventoDTO> historicoDoRegistro(@PathVariable String entidade, @PathVariable String entidadeId) {
        return repository.findByEntidadeAndEntidadeIdOrderByCriadoEmDesc(entidade, entidadeId)
                .stream().map(this::toDTO).toList();
    }

    private EventoDTO toDTO(EventoAuditoria e) {
        return new EventoDTO(
                String.valueOf(e.getId()),
                e.getUsuarioId() == null ? "" : String.valueOf(e.getUsuarioId()),
                e.getUsuarioNome(),
                e.getEntidade(),
                e.getEntidadeId(),
                e.getEntidadeDescricao(),
                e.getAcao().name(),
                e.getDescricao(),
                lerJson(e.getDadosAnteriores()),
                lerJson(e.getDadosNovos()),
                e.getCriadoEm().toString());
    }

    private Map<String, Object> lerJson(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception ex) {
            return null;
        }
    }
}
