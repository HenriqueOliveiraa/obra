package com.obra.obra.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaTrabalhoResponseDTO {

    private Long id;
    private LocalDate data;
    private Boolean trabalhou;
    private String observacao;
}
