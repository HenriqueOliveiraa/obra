package com.obra.obra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "eventos_auditoria")
@AllArgsConstructor
@NoArgsConstructor
public class EventoAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private String usuarioNome;

    private String entidade;
    private String entidadeId;
    private String entidadeDescricao;

    @Enumerated(EnumType.STRING)
    private AcaoAuditoria acao;

    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String dadosAnteriores;

    @Column(columnDefinition = "TEXT")
    private String dadosNovos;

    private LocalDateTime criadoEm;
}
