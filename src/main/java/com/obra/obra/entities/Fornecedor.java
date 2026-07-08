package com.obra.obra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "fornecedores")
@AllArgsConstructor
@NoArgsConstructor
public class Fornecedor extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String especialidade;

    @Column(columnDefinition = "TEXT")
    private String observacao;
}
