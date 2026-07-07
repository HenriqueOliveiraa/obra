package com.obra.obra.dtos.response;

import com.obra.obra.entities.CategoriaGasto;
import com.obra.obra.entities.CategoriaOrcamento;
import com.obra.obra.entities.StatusMaterial;
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
    private StatusMaterial statusMaterial;
    private String responsavelRecebimento;
    private CategoriaOrcamento etapaCategoria;
    private String subcategoria;
    private List<ParcelaResponseDTO> parcelas;
}
