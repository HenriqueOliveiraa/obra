package com.obra.obra.config;

import com.obra.obra.entities.AcaoAuditoria;
import com.obra.obra.entities.AuditEntity;
import com.obra.obra.services.AuditoriaRegistro;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Escuta inserts/updates/deletes do Hibernate nas entidades auditadas
 * (as que estendem AuditEntity) e grava um EventoAuditoria para cada acao.
 */
@Component
public class AuditoriaHibernateListener
        implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    // Campos que nao entram no diff: colecoes, base64 gigante e os proprios campos de auditoria
    private static final Set<String> CAMPOS_IGNORADOS = Set.of(
            "parcelas", "dataUrl", "criadoPor", "atualizadoPor", "criadoEm", "atualizadoEm");

    private static final Object IGNORAR = new Object();

    private final ObjectProvider<AuditoriaRegistro> registroProvider;

    public AuditoriaHibernateListener(ObjectProvider<AuditoriaRegistro> registroProvider) {
        this.registroProvider = registroProvider;
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof AuditEntity) {
            registroProvider.getObject().registrar(
                    AcaoAuditoria.CRIACAO, event.getEntity(), event.getId(), null, null);
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (!(event.getEntity() instanceof AuditEntity)) {
            return;
        }

        Map<String, Object> antes = new LinkedHashMap<>();
        Map<String, Object> depois = new LinkedHashMap<>();
        String[] propriedades = event.getPersister().getPropertyNames();
        Object[] estadoAntigo = event.getOldState();
        Object[] estadoNovo = event.getState();

        if (estadoAntigo != null && estadoNovo != null) {
            for (int i = 0; i < propriedades.length; i++) {
                String nome = propriedades[i];
                if (CAMPOS_IGNORADOS.contains(nome)) {
                    continue;
                }
                Object valorAntigo = normalizar(estadoAntigo[i]);
                Object valorNovo = normalizar(estadoNovo[i]);
                if (valorAntigo == IGNORAR || valorNovo == IGNORAR) {
                    continue;
                }
                if (!Objects.equals(valorAntigo, valorNovo)) {
                    antes.put(nome, valorAntigo);
                    depois.put(nome, valorNovo);
                }
            }
        }

        if (estadoAntigo != null && antes.isEmpty()) {
            return; // nada relevante mudou
        }

        registroProvider.getObject().registrar(
                AcaoAuditoria.EDICAO, event.getEntity(), event.getId(),
                antes.isEmpty() ? null : antes,
                depois.isEmpty() ? null : depois);
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        if (event.getEntity() instanceof AuditEntity) {
            registroProvider.getObject().registrar(
                    AcaoAuditoria.EXCLUSAO, event.getEntity(), event.getId(), null, null);
        }
    }

    private Object normalizar(Object valor) {
        if (valor == null) return null;
        if (valor instanceof Enum<?> e) return e.name();
        if (valor instanceof BigDecimal b) return b;
        if (valor instanceof Number || valor instanceof Boolean || valor instanceof String) return valor;
        if (valor instanceof LocalDate || valor instanceof LocalDateTime) return valor.toString();
        return IGNORAR; // colecoes, associacoes e tipos complexos ficam fora do diff
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}
