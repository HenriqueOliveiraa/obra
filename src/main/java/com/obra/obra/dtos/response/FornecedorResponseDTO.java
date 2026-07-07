package com.obra.obra.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FornecedorResponseDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String especialidade;
    private String observacao;
}
