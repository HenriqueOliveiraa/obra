package com.obra.obra.repositories;

import com.obra.obra.entities.Cotacao;
import com.obra.obra.entities.CategoriaOrcamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {
    List<Cotacao> findByCategoriaAndSubcategoria(CategoriaOrcamento categoria, String subcategoria);
}
