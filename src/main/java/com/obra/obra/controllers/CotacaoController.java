package com.obra.obra.controllers;

import com.obra.obra.dtos.request.CotacaoRequestDTO;
import com.obra.obra.dtos.response.CotacaoResponseDTO;
import com.obra.obra.entities.Cotacao;
import com.obra.obra.services.CotacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cotacoes")
@RequiredArgsConstructor
public class CotacaoController {

    private final CotacaoService service;

    @PostMapping
    public ResponseEntity<CotacaoResponseDTO> criar(@Valid @RequestBody CotacaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(service.criar(dto)));
    }

    @GetMapping
    public List<CotacaoResponseDTO> listarTodos() {
        return service.listarTodos().stream().map(this::toDTO).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CotacaoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CotacaoRequestDTO dto) {
        return ResponseEntity.ok(toDTO(service.atualizar(id, dto)));
    }

    @PatchMapping("/{id}/escolher")
    public ResponseEntity<CotacaoResponseDTO> marcarEscolhido(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(service.marcarEscolhido(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok(Map.of("mensagem", "Cotação deletada com sucesso"));
    }

    private CotacaoResponseDTO toDTO(Cotacao c) {
        return new CotacaoResponseDTO(c.getId(), c.getCategoria(), c.getSubcategoria(),
                c.getSubcategoriaCustom(), c.getItem(), c.getFornecedor(), c.getValor(),
                c.getQuantidade(), c.getUnidade(), c.getData(), c.getObservacao(), c.getEscolhido());
    }
}
