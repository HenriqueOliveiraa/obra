package com.obra.obra.repositories;

import com.obra.obra.entities.Gasto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    Page<Gasto> findAll(Pageable pageable);
}
