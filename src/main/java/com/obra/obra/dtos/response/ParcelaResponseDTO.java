package com.obra.obra.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelaResponseDTO {

    private Long id;
    private Integer numero;
    private BigDecimal valor;
    private LocalDate vencimento;
    private Boolean pago;
    private LocalDate dataPagamento;
}
