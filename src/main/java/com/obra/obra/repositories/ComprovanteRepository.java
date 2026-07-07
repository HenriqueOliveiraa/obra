package com.obra.obra.repositories;

import com.obra.obra.entities.Comprovante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComprovanteRepository extends JpaRepository<Comprovante, Long> {
    Optional<Comprovante> findByGastoId(Long gastoId);
    void deleteByGastoId(Long gastoId);
}
