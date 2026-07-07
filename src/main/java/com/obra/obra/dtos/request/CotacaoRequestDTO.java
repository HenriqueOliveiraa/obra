package com.obra.obra.dtos.request;

import com.obra.obra.entities.CategoriaOrcamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CotacaoRequestDTO {
    @NotNull
    private CategoriaOrcamento categoria;
    @NotBlank
    private String subcategoria;
    private String subcategoriaCustom;
    @NotBlank
    private String item;
    private String fornecedor;
    @NotNull
    @Positive
    private BigDecimal valor;
    private Integer quantidade;
    private String unidade;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;
    private String observacao;
}
