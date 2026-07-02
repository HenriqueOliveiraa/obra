package com.obra.obra.repositories;

import com.obra.obra.entities.DiaTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaTrabalhoRepository extends JpaRepository<DiaTrabalho, Long> {
    List<DiaTrabalho> findByDataBetween(LocalDate inicio, LocalDate fim);
    Optional<DiaTrabalho> findByData(LocalDate data);
}
