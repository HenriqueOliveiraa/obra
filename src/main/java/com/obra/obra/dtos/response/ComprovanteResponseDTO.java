package com.obra.obra.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComprovanteResponseDTO {
    private Long id;
    private Long gastoId;
    private String nomeArquivo;
    private String tipoArquivo;
    private String dataUrl;
    private String dataUpload;
}
