package com.obra.obra.dtos.request;

import com.obra.obra.entities.CategoriaGasto;
import com.obra.obra.entities.PrioridadeCompra;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCompraRequestDTO {
    @NotBlank
    private String descricao;
    @NotNull
    private CategoriaGasto categoria;
    private Integer quantidadeDesejada;
    @NotNull
    private PrioridadeCompra prioridade;
    private String observacao;
    private Boolean comprado = false;
}
