package com.obra.obra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "gastos")
@AllArgsConstructor
@NoArgsConstructor
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private CategoriaGasto categoria;

    private Integer quantidade;
    private BigDecimal valorTotal;
    private Boolean parcelado;
    private Integer numeroParcelas;
    private Integer diaVencimento;
    private LocalDate dataCompra;
    private String fornecedor;

    @Enumerated(EnumType.STRING)
    private StatusMaterial statusMaterial;

    private String responsavelRecebimento;

    @Enumerated(EnumType.STRING)
    private CategoriaOrcamento etapaCategoria;

    private String subcategoria;

    @OneToMany(mappedBy = "gasto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parcela> parcelas = new ArrayList<>();
}
