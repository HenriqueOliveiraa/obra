package com.obra.obra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "orcamento_config")
@AllArgsConstructor
@NoArgsConstructor
public class OrcamentoConfig {

    @Id
    private Long id = 1L;

    private BigDecimal orcamentoTotal = BigDecimal.ZERO;
    private BigDecimal tetoMaterial = BigDecimal.ZERO;
    private BigDecimal tetoMaoDeObra = BigDecimal.ZERO;
    private BigDecimal tetoTransporte = BigDecimal.ZERO;
    private BigDecimal tetoOutros = BigDecimal.ZERO;
    private BigDecimal margemTolerancia = BigDecimal.valueOf(10);
}
