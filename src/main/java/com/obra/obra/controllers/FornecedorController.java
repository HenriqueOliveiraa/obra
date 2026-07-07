package com.obra.obra.controllers;

import com.obra.obra.dtos.request.FornecedorRequestDTO;
import com.obra.obra.dtos.response.FornecedorResponseDTO;
import com.obra.obra.entities.Fornecedor;
import com.obra.obra.services.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService service;

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> criar(@Valid @RequestBody FornecedorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(service.criar(dto)));
    }

    @GetMapping
    public List<FornecedorResponseDTO> listarTodos() {
        return service.listarTodos().stream().map(this::toDTO).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FornecedorRequestDTO dto) {
        return ResponseEntity.ok(toDTO(service.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok(Map.of("mensagem", "Fornecedor deletado com sucesso"));
    }

    private FornecedorResponseDTO toDTO(Fornecedor f) {
        return new FornecedorResponseDTO(f.getId(), f.getNome(), f.getTelefone(), f.getEspecialidade(), f.getObservacao());
    }
}
