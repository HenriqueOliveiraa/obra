package com.obra.obra.dtos.response;

import com.obra.obra.entities.CategoriaGasto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaGastoDTO {

    private CategoriaGasto categoria;
    private BigDecimal totalGasto;
}
