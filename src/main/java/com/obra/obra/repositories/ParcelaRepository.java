package com.obra.obra.repositories;

import com.obra.obra.entities.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    long countByPago(boolean pago);
}
