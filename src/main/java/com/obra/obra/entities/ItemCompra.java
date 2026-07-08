package com.obra.obra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "itens_compra")
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompra extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private CategoriaGasto categoria;

    private Integer quantidadeDesejada;

    @Enumerated(EnumType.STRING)
    private PrioridadeCompra prioridade;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    private Boolean comprado = false;
}
