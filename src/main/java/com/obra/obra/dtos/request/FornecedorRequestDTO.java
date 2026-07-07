package com.obra.obra.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FornecedorRequestDTO {
    @NotBlank
    private String nome;
    private String telefone;
    private String especialidade;
    private String observacao;
}
