package com.obra.obra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "cotacoes")
@AllArgsConstructor
@NoArgsConstructor
public class Cotacao extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoriaOrcamento categoria;

    private String subcategoria;
    private String subcategoriaCustom;
    private String item;
    private String fornecedor;
    private BigDecimal valor;
    private Integer quantidade;
    private String unidade;
    private LocalDate data;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    private Boolean escolhido = false;
}
