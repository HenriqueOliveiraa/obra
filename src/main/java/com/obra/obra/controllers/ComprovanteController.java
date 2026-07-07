package com.obra.obra.controllers;

import com.obra.obra.dtos.request.ComprovanteRequestDTO;
import com.obra.obra.dtos.response.ComprovanteResponseDTO;
import com.obra.obra.entities.Comprovante;
import com.obra.obra.services.ComprovanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comprovantes")
@RequiredArgsConstructor
public class ComprovanteController {

    private final ComprovanteService service;

    @PostMapping
    public ResponseEntity<ComprovanteResponseDTO> salvar(@Valid @RequestBody ComprovanteRequestDTO dto) {
        return ResponseEntity.ok(toDTO(service.salvar(dto)));
    }

    @GetMapping
    public List<ComprovanteResponseDTO> listarTodos() {
        return service.listarTodos().stream().map(this::toDTO).toList();
    }

    @GetMapping("/gasto/{gastoId}")
    public ResponseEntity<ComprovanteResponseDTO> buscarPorGasto(@PathVariable Long gastoId) {
        return service.buscarPorGasto(gastoId)
                .map(c -> ResponseEntity.ok(toDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/gasto/{gastoId}")
    public ResponseEntity<Map<String, String>> remover(@PathVariable Long gastoId) {
        service.remover(gastoId);
        return ResponseEntity.ok(Map.of("mensagem", "Comprovante removido com sucesso"));
    }

    private ComprovanteResponseDTO toDTO(Comprovante c) {
        return new ComprovanteResponseDTO(c.getId(), c.getGastoId(), c.getNomeArquivo(),
                c.getTipoArquivo(), c.getDataUrl(), c.getDataUpload());
    }
}
