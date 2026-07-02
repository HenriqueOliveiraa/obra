package com.obra.obra.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "parcelas")
@AllArgsConstructor
@NoArgsConstructor
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gasto_id")
    @JsonIgnore
    private Gasto gasto;

    private Integer numero;
    private BigDecimal valor;
    private LocalDate vencimento;
    private Boolean pago;
    private LocalDate dataPagamento;
}
