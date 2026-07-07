package com.obra.obra.services;

import com.obra.obra.dtos.request.FornecedorRequestDTO;
import com.obra.obra.entities.Fornecedor;
import com.obra.obra.repositories.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository repository;

    public Fornecedor criar(FornecedorRequestDTO dto) {
        Fornecedor f = new Fornecedor();
        aplicarCampos(f, dto);
        return repository.save(f);
    }

    public List<Fornecedor> listarTodos() {
        return repository.findAll();
    }

    public Fornecedor atualizar(Long id, FornecedorRequestDTO dto) {
        Fornecedor f = buscarPorId(id);
        aplicarCampos(f, dto);
        return repository.save(f);
    }

    public void deletar(Long id) {
        repository.delete(buscarPorId(id));
    }

    private Fornecedor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
    }

    private void aplicarCampos(Fornecedor f, FornecedorRequestDTO dto) {
        f.setNome(dto.getNome());
        f.setTelefone(dto.getTelefone());
        f.setEspecialidade(dto.getEspecialidade());
        f.setObservacao(dto.getObservacao());
    }
}
