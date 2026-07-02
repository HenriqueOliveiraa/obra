package com.obra.obra.controllers;

import com.obra.obra.dtos.request.DiaTrabalhoRequestDTO;
import com.obra.obra.dtos.response.DiaTrabalhoResponseDTO;
import com.obra.obra.entities.DiaTrabalho;
import com.obra.obra.services.DiaTrabalhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dias-trabalho")
@RequiredArgsConstructor
public class DiaTrabalhoController {

    private final DiaTrabalhoService service;

    @PostMapping
    public ResponseEntity<DiaTrabalhoResponseDTO> criar(@Valid @RequestBody DiaTrabalhoRequestDTO requestDTO) {
        DiaTrabalho dia = service.criar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(dia));
    }

    @GetMapping
    public List<DiaTrabalhoResponseDTO> listar(@RequestParam(required = false) Integer ano,
                                                @RequestParam(required = false) Integer mes) {
        List<DiaTrabalho> dias = (ano != null && mes != null)
                ? service.listarPorMes(ano, mes)
                : service.listarTodos();

        return dias.stream().map(this::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaTrabalhoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toResponseDTO(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaTrabalhoResponseDTO> atualizar(@PathVariable Long id,
                                                             @Valid @RequestBody DiaTrabalhoRequestDTO requestDTO) {
        return ResponseEntity.ok(toResponseDTO(service.atualizar(id, requestDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        service.deletar(id);
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Dia de trabalho deletado com sucesso");
        return ResponseEntity.ok(resposta);
    }

    private DiaTrabalhoResponseDTO toResponseDTO(DiaTrabalho dia) {
        return new DiaTrabalhoResponseDTO(dia.getId(), dia.getData(), dia.getTrabalhou(), dia.getObservacao());
    }
}
