package com.obra.obra.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemAgregadoDTO {

    private String descricao;
    private Integer quantidadeTotal;
    private BigDecimal valorTotalGasto;
}
