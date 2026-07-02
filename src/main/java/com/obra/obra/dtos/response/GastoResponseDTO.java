package com.obra.obra.dtos.response;

import com.obra.obra.entities.CategoriaGasto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoResponseDTO {

    private Long id;
    private String descricao;
    private CategoriaGasto categoria;
    private Integer quantidade;
    private BigDecimal valorTotal;
    private Boolean parcelado;
    private Integer numeroParcelas;
    private Integer diaVencimento;
    private LocalDate dataCompra;
    private String fornecedor;
    private List<ParcelaResponseDTO> parcelas;
}
