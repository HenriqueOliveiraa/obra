package com.obra.obra.services;

import com.obra.obra.dtos.request.ComprovanteRequestDTO;
import com.obra.obra.entities.Comprovante;
import com.obra.obra.repositories.ComprovanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComprovanteService {

    private final ComprovanteRepository repository;

    @Transactional
    public Comprovante salvar(ComprovanteRequestDTO dto) {
        repository.findByGastoId(dto.getGastoId())
                .ifPresent(c -> repository.deleteByGastoId(c.getGastoId()));

        Comprovante c = new Comprovante();
        c.setGastoId(dto.getGastoId());
        c.setNomeArquivo(dto.getNomeArquivo());
        c.setTipoArquivo(dto.getTipoArquivo());
        c.setDataUrl(dto.getDataUrl());
        c.setDataUpload(dto.getDataUpload());
        return repository.save(c);
    }

    public List<Comprovante> listarTodos() {
        return repository.findAll();
    }

    public Optional<Comprovante> buscarPorGasto(Long gastoId) {
        return repository.findByGastoId(gastoId);
    }

    @Transactional
    public void remover(Long gastoId) {
        repository.deleteByGastoId(gastoId);
    }
}
