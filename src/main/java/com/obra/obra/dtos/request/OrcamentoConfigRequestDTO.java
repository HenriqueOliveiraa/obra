package com.obra.obra.dtos.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrcamentoConfigRequestDTO {
    private BigDecimal orcamentoTotal;
    private BigDecimal tetoMaterial;
    private BigDecimal tetoMaoDeObra;
    private BigDecimal tetoTransporte;
    private BigDecimal tetoOutros;
    private BigDecimal margemTolerancia;
}
