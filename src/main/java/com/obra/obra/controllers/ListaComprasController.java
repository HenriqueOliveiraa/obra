package com.obra.obra.controllers;

import com.obra.obra.dtos.request.ItemCompraRequestDTO;
import com.obra.obra.dtos.response.ItemCompraResponseDTO;
import com.obra.obra.entities.ItemCompra;
import com.obra.obra.services.ListaComprasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lista-compras")
@RequiredArgsConstructor
public class ListaComprasController {

    private final ListaComprasService service;

    @PostMapping
    public ResponseEntity<ItemCompraResponseDTO> criar(@Valid @RequestBody ItemCompraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(service.criar(dto)));
    }

    @GetMapping
    public List<ItemCompraResponseDTO> listarTodos() {
        return service.listarTodos().stream().map(this::toDTO).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCompraResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ItemCompraRequestDTO dto) {
        return ResponseEntity.ok(toDTO(service.atualizar(id, dto)));
    }

    @PatchMapping("/{id}/marcar-comprado")
    public ResponseEntity<ItemCompraResponseDTO> marcarComprado(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(service.marcarComprado(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok(Map.of("mensagem", "Item removido com sucesso"));
    }

    private ItemCompraResponseDTO toDTO(ItemCompra item) {
        return new ItemCompraResponseDTO(item.getId(), item.getDescricao(), item.getCategoria(),
                item.getQuantidadeDesejada(), item.getPrioridade(), item.getObservacao(), item.getComprado());
    }
}
