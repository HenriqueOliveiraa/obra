package com.obra.obra.controllers;

import com.obra.obra.dtos.request.OrcamentoConfigRequestDTO;
import com.obra.obra.dtos.response.OrcamentoConfigResponseDTO;
import com.obra.obra.entities.OrcamentoConfig;
import com.obra.obra.services.OrcamentoConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orcamento-config")
@RequiredArgsConstructor
public class OrcamentoConfigController {

    private final OrcamentoConfigService service;

    @GetMapping
    public OrcamentoConfigResponseDTO obter() {
        return toDTO(service.obter());
    }

    @PutMapping
    public ResponseEntity<OrcamentoConfigResponseDTO> salvar(@RequestBody OrcamentoConfigRequestDTO dto) {
        return ResponseEntity.ok(toDTO(service.salvar(dto)));
    }

    private OrcamentoConfigResponseDTO toDTO(OrcamentoConfig c) {
        return new OrcamentoConfigResponseDTO(c.getOrcamentoTotal(), c.getTetoMaterial(),
                c.getTetoMaoDeObra(), c.getTetoTransporte(), c.getTetoOutros(), c.getMargemTolerancia());
    }
}
