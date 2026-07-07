package com.obra.obra.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComprovanteRequestDTO {
    @NotNull
    private Long gastoId;
    private String nomeArquivo;
    private String tipoArquivo;
    private String dataUrl;
    private String dataUpload;
}
