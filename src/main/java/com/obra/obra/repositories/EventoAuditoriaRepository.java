package com.obra.obra.repositories;

import com.obra.obra.entities.AcaoAuditoria;
import com.obra.obra.entities.EventoAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoAuditoriaRepository extends JpaRepository<EventoAuditoria, Long> {

    @Query("""
            select e from EventoAuditoria e
            where (:usuarioId is null or e.usuarioId = :usuarioId)
              and (cast(:entidade as string) is null or e.entidade = :entidade)
              and (:acao is null or e.acao = :acao)
              and (cast(:inicio as timestamp) is null or e.criadoEm >= :inicio)
              and (cast(:fim as timestamp) is null or e.criadoEm <= :fim)
              and (cast(:busca as string) is null
                   or lower(e.descricao) like lower(concat('%', cast(:busca as string), '%'))
                   or lower(coalesce(e.entidadeDescricao, '')) like lower(concat('%', cast(:busca as string), '%')))
            """)
    Page<EventoAuditoria> buscar(@Param("usuarioId") Long usuarioId,
                                 @Param("entidade") String entidade,
                                 @Param("acao") AcaoAuditoria acao,
                                 @Param("inicio") LocalDateTime inicio,
                                 @Param("fim") LocalDateTime fim,
                                 @Param("busca") String busca,
                                 Pageable pageable);

    List<EventoAuditoria> findByEntidadeAndEntidadeIdOrderByCriadoEmDesc(String entidade, String entidadeId);
}
