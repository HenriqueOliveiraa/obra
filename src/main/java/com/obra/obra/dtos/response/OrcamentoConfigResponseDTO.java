package com.obra.obra.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrcamentoConfigResponseDTO {
    private BigDecimal orcamentoTotal;
    private BigDecimal tetoMaterial;
    private BigDecimal tetoMaoDeObra;
    private BigDecimal tetoTransporte;
    private BigDecimal tetoOutros;
    private BigDecimal margemTolerancia;
}
