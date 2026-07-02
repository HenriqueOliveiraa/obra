package com.obra.obra.services;

import com.obra.obra.dtos.request.DiaTrabalhoRequestDTO;
import com.obra.obra.entities.DiaTrabalho;
import com.obra.obra.repositories.DiaTrabalhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaTrabalhoService {

    private final DiaTrabalhoRepository repository;

    public DiaTrabalho criar(DiaTrabalhoRequestDTO requestDTO) {
        DiaTrabalho dia = new DiaTrabalho();

        dia.setData(requestDTO.getData());
        dia.setTrabalhou(requestDTO.getTrabalhou());
        dia.setObservacao(requestDTO.getObservacao());

        return repository.save(dia);
    }

    public DiaTrabalho atualizar(Long id, DiaTrabalhoRequestDTO requestDTO) {
        DiaTrabalho dia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dia de trabalho não encontrado"));

        dia.setData(requestDTO.getData());
        dia.setTrabalhou(requestDTO.getTrabalhou());
        dia.setObservacao(requestDTO.getObservacao());

        return repository.save(dia);
    }

    public List<DiaTrabalho> listarTodos() {
        return repository.findAll();
    }

    public List<DiaTrabalho> listarPorMes(int ano, int mes) {
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
        return repository.findByDataBetween(inicio, fim);
    }

    public Optional<DiaTrabalho> buscarPorData(LocalDate data) {
        return repository.findByData(data);
    }

    public DiaTrabalho registrarOuAtualizar(DiaTrabalhoRequestDTO requestDTO) {
        DiaTrabalho dia = repository.findByData(requestDTO.getData()).orElseGet(DiaTrabalho::new);

        dia.setData(requestDTO.getData());
        dia.setTrabalhou(requestDTO.getTrabalhou());
        dia.setObservacao(requestDTO.getObservacao());

        return repository.save(dia);
    }

    public DiaTrabalho buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dia de trabalho não encontrado"));
    }

    public void deletar(Long id) {
        DiaTrabalho dia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dia de trabalho não encontrado"));

        repository.delete(dia);
    }
}
