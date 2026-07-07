package com.obra.obra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "comprovantes")
@AllArgsConstructor
@NoArgsConstructor
public class Comprovante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gastoId;
    private String nomeArquivo;
    private String tipoArquivo;

    @Column(columnDefinition = "TEXT")
    private String dataUrl;

    private String dataUpload;
}
