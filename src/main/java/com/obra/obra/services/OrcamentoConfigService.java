package com.obra.obra.services;

import com.obra.obra.dtos.request.OrcamentoConfigRequestDTO;
import com.obra.obra.entities.OrcamentoConfig;
import com.obra.obra.repositories.OrcamentoConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrcamentoConfigService {

    private final OrcamentoConfigRepository repository;

    public OrcamentoConfig obter() {
        return repository.findById(1L).orElseGet(() -> repository.save(new OrcamentoConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(10))));
    }

    public OrcamentoConfig salvar(OrcamentoConfigRequestDTO dto) {
        OrcamentoConfig config = obter();
        if (dto.getOrcamentoTotal() != null) config.setOrcamentoTotal(dto.getOrcamentoTotal());
        if (dto.getTetoMaterial() != null) config.setTetoMaterial(dto.getTetoMaterial());
        if (dto.getTetoMaoDeObra() != null) config.setTetoMaoDeObra(dto.getTetoMaoDeObra());
        if (dto.getTetoTransporte() != null) config.setTetoTransporte(dto.getTetoTransporte());
        if (dto.getTetoOutros() != null) config.setTetoOutros(dto.getTetoOutros());
        if (dto.getMargemTolerancia() != null) config.setMargemTolerancia(dto.getMargemTolerancia());
        return repository.save(config);
    }
}
