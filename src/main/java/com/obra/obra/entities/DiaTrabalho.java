package com.obra.obra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "dias_trabalho", uniqueConstraints = @UniqueConstraint(columnNames = "data"))
@AllArgsConstructor
@NoArgsConstructor
public class DiaTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private Boolean trabalhou;
    private String observacao;
}
