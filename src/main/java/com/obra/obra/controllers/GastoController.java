package com.obra.obra.controllers;

import com.obra.obra.dtos.request.GastoRequestDTO;
import com.obra.obra.dtos.response.DashboardResponseDTO;
import com.obra.obra.dtos.response.GastoResponseDTO;
import com.obra.obra.dtos.response.ParcelaResponseDTO;
import com.obra.obra.dtos.response.ResumoGastosResponseDTO;
import com.obra.obra.entities.Gasto;
import com.obra.obra.entities.Parcela;
import com.obra.obra.services.GastoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gastos")
@RequiredArgsConstructor
public class GastoController {

    private final GastoService service;

    @PostMapping
    public ResponseEntity<GastoResponseDTO> criar(@Valid @RequestBody GastoRequestDTO requestDTO) {
        Gasto gasto = service.criar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(gasto));
    }

    @GetMapping
    public Page<GastoResponseDTO> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        return service.listarTodos(pageable).map(this::toResponseDTO);
    }

    @GetMapping("/resumo")
    public ResumoGastosResponseDTO resumo() {
        GastoService.ResumoGastos resumo = service.resumo();
        return new ResumoGastosResponseDTO(
                resumo.totalGasto(),
                resumo.totalPago(),
                resumo.totalPendente(),
                resumo.parcelasPagas(),
                resumo.parcelasPendentes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toResponseDTO(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody GastoRequestDTO requestDTO) {
        Gasto gasto = service.atualizar(id, requestDTO);
        return ResponseEntity.ok(toResponseDTO(gasto));
    }

    @GetMapping("/dashboard")
    public DashboardResponseDTO dashboard() {
        return new DashboardResponseDTO(service.materiaisMaisComprados(), service.gastoPorCategoria());
    }

    @PatchMapping("/{gastoId}/parcelas/{parcelaId}/pagar")
    public ResponseEntity<ParcelaResponseDTO> pagarParcela(@PathVariable Long gastoId, @PathVariable Long parcelaId) {
        Parcela parcela = service.marcarParcelaComoPaga(gastoId, parcelaId);
        return ResponseEntity.ok(toParcelaResponseDTO(parcela));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        service.deletar(id);
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Gasto deletado com sucesso");
        return ResponseEntity.ok(resposta);
    }

    private GastoResponseDTO toResponseDTO(Gasto gasto) {
        return new GastoResponseDTO(
                gasto.getId(),
                gasto.getDescricao(),
                gasto.getCategoria(),
                gasto.getQuantidade(),
                gasto.getValorTotal(),
                gasto.getParcelado(),
                gasto.getNumeroParcelas(),
                gasto.getDiaVencimento(),
                gasto.getDataCompra(),
                gasto.getFornecedor(),
                gasto.getParcelas().stream().map(this::toParcelaResponseDTO).toList()
        );
    }

    private ParcelaResponseDTO toParcelaResponseDTO(Parcela parcela) {
        return new ParcelaResponseDTO(
                parcela.getId(),
                parcela.getNumero(),
                parcela.getValor(),
                parcela.getVencimento(),
                parcela.getPago(),
                parcela.getDataPagamento()
        );
    }
}
