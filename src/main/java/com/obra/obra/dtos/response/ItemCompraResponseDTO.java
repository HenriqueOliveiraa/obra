package com.obra.obra.dtos.response;

import com.obra.obra.entities.CategoriaGasto;
import com.obra.obra.entities.PrioridadeCompra;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemCompraResponseDTO {
    private Long id;
    private String descricao;
    private CategoriaGasto categoria;
    private Integer quantidadeDesejada;
    private PrioridadeCompra prioridade;
    private String observacao;
    private Boolean comprado;
}
