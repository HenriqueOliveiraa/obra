package com.obra.obra.dtos.request;

import com.obra.obra.entities.CategoriaGasto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class GastoRequestDTO {

    @NotBlank
    private String descricao;

    @NotNull
    private CategoriaGasto categoria;

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    @Positive
    private BigDecimal valorTotal;

    @NotNull
    private Boolean parcelado;

    @Min(1)
    private Integer numeroParcelas;

    @Min(1)
    @Max(31)
    private Integer diaVencimento;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataCompra;

    private String fornecedor;
}
