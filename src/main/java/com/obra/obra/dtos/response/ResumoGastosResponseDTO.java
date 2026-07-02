package com.obra.obra.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumoGastosResponseDTO {

    private BigDecimal totalGasto;
    private BigDecimal totalPago;
    private BigDecimal totalPendente;
    private long parcelasPagas;
    private long parcelasPendentes;
}
