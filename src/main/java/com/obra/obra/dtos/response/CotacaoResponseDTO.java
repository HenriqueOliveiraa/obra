package com.obra.obra.dtos.response;

import com.obra.obra.entities.CategoriaOrcamento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CotacaoResponseDTO {
    private Long id;
    private CategoriaOrcamento categoria;
    private String subcategoria;
    private String subcategoriaCustom;
    private String item;
    private String fornecedor;
    private BigDecimal valor;
    private Integer quantidade;
    private String unidade;
    private LocalDate data;
    private String observacao;
    private Boolean escolhido;
}
