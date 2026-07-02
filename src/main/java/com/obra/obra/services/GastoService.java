package com.obra.obra.services;

import com.obra.obra.dtos.request.GastoRequestDTO;
import com.obra.obra.dtos.response.CategoriaGastoDTO;
import com.obra.obra.dtos.response.ItemAgregadoDTO;
import com.obra.obra.entities.CategoriaGasto;
import com.obra.obra.entities.Gasto;
import com.obra.obra.entities.Parcela;
import com.obra.obra.repositories.GastoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GastoService {

    private static final int DIA_VENCIMENTO_PADRAO = 12;

    private final GastoRepository repository;

    public Gasto criar(GastoRequestDTO requestDTO) {
        Gasto gasto = new Gasto();
        aplicarCamposBasicos(gasto, requestDTO);

        int numeroParcelas = Boolean.TRUE.equals(requestDTO.getParcelado())
                ? requestDTO.getNumeroParcelas()
                : 1;
        gasto.setNumeroParcelas(numeroParcelas);
        gasto.setParcelas(gerarParcelas(gasto, numeroParcelas));

        return repository.save(gasto);
    }

    public Gasto atualizar(Long id, GastoRequestDTO requestDTO) {
        Gasto gasto = buscarPorId(id);

        boolean afetaParcelas = afetaCalculoDeParcelas(gasto, requestDTO);

        aplicarCamposBasicos(gasto, requestDTO);

        if (afetaParcelas) {
            int numeroParcelas = Boolean.TRUE.equals(requestDTO.getParcelado())
                    ? requestDTO.getNumeroParcelas()
                    : 1;
            gasto.setNumeroParcelas(numeroParcelas);
            gasto.getParcelas().clear();
            gasto.getParcelas().addAll(gerarParcelas(gasto, numeroParcelas));
        }

        return repository.save(gasto);
    }

    private boolean afetaCalculoDeParcelas(Gasto gasto, GastoRequestDTO requestDTO) {
        int numeroParcelasNovo = Boolean.TRUE.equals(requestDTO.getParcelado())
                ? requestDTO.getNumeroParcelas()
                : 1;

        Integer diaVencimentoNovo = Boolean.TRUE.equals(requestDTO.getParcelado())
                ? diaVencimentoOuPadrao(requestDTO.getDiaVencimento())
                : null;

        return gasto.getValorTotal().compareTo(requestDTO.getValorTotal()) != 0
                || !Objects.equals(gasto.getParcelado(), requestDTO.getParcelado())
                || !Objects.equals(gasto.getNumeroParcelas(), numeroParcelasNovo)
                || !Objects.equals(gasto.getDiaVencimento(), diaVencimentoNovo)
                || !Objects.equals(gasto.getDataCompra(), requestDTO.getDataCompra());
    }

    private void aplicarCamposBasicos(Gasto gasto, GastoRequestDTO requestDTO) {
        gasto.setDescricao(requestDTO.getDescricao());
        gasto.setCategoria(requestDTO.getCategoria());
        gasto.setQuantidade(requestDTO.getQuantidade());
        gasto.setValorTotal(requestDTO.getValorTotal());
        gasto.setParcelado(requestDTO.getParcelado());
        gasto.setDataCompra(requestDTO.getDataCompra());
        gasto.setFornecedor(requestDTO.getFornecedor());
        gasto.setDiaVencimento(Boolean.TRUE.equals(requestDTO.getParcelado())
                ? diaVencimentoOuPadrao(requestDTO.getDiaVencimento())
                : null);
    }

    private int diaVencimentoOuPadrao(Integer diaVencimento) {
        return diaVencimento != null ? diaVencimento : DIA_VENCIMENTO_PADRAO;
    }

    private List<Parcela> gerarParcelas(Gasto gasto, int numeroParcelas) {
        List<Parcela> parcelas = new ArrayList<>();

        BigDecimal valorParcela = gasto.getValorTotal()
                .divide(BigDecimal.valueOf(numeroParcelas), 2, RoundingMode.DOWN);
        BigDecimal somaParcelas = valorParcela.multiply(BigDecimal.valueOf(numeroParcelas));
        BigDecimal diferenca = gasto.getValorTotal().subtract(somaParcelas);

        boolean pagoNoAto = !Boolean.TRUE.equals(gasto.getParcelado());
        int diaVencimento = diaVencimentoOuPadrao(gasto.getDiaVencimento());
        YearMonth mesPrimeiraParcela = calcularMesPrimeiraParcela(gasto.getDataCompra(), diaVencimento);

        for (int i = 1; i <= numeroParcelas; i++) {
            Parcela parcela = new Parcela();
            parcela.setGasto(gasto);
            parcela.setNumero(i);

            BigDecimal valor = (i == numeroParcelas) ? valorParcela.add(diferenca) : valorParcela;
            parcela.setValor(valor);

            parcela.setVencimento(pagoNoAto ? gasto.getDataCompra() : dataVencimentoDaParcela(mesPrimeiraParcela, i, diaVencimento));
            parcela.setPago(pagoNoAto);
            parcela.setDataPagamento(pagoNoAto ? gasto.getDataCompra() : null);

            parcelas.add(parcela);
        }

        return parcelas;
    }

    /**
     * Se a compra já passou do dia de vencimento informado nesse mês, a primeira parcela cai no mês seguinte.
     */
    private YearMonth calcularMesPrimeiraParcela(LocalDate dataCompra, int diaVencimento) {
        return dataCompra.getDayOfMonth() < diaVencimento
                ? YearMonth.from(dataCompra)
                : YearMonth.from(dataCompra).plusMonths(1);
    }

    /**
     * Usa o dia informado, ajustando para o último dia do mês quando ele não existir (ex: dia 31 em fevereiro).
     */
    private LocalDate dataVencimentoDaParcela(YearMonth mesPrimeiraParcela, int numeroParcela, int diaVencimento) {
        YearMonth mes = mesPrimeiraParcela.plusMonths(numeroParcela - 1);
        int dia = Math.min(diaVencimento, mes.lengthOfMonth());
        return mes.atDay(dia);
    }

    public Page<Gasto> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Gasto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto não encontrado"));
    }

    public void deletar(Long id) {
        Gasto gasto = buscarPorId(id);
        repository.delete(gasto);
    }

    public Parcela marcarParcelaComoPaga(Long gastoId, Long parcelaId) {
        Gasto gasto = buscarPorId(gastoId);

        Parcela parcela = gasto.getParcelas().stream()
                .filter(p -> p.getId().equals(parcelaId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parcela não encontrada"));

        parcela.setPago(true);
        parcela.setDataPagamento(java.time.LocalDate.now());

        repository.save(gasto);
        return parcela;
    }

    public ResumoGastos resumo() {
        List<Gasto> todos = repository.findAll();

        BigDecimal totalGasto = BigDecimal.ZERO;
        BigDecimal totalPago = BigDecimal.ZERO;
        BigDecimal totalPendente = BigDecimal.ZERO;
        long parcelasPagas = 0;
        long parcelasPendentes = 0;

        for (Gasto gasto : todos) {
            totalGasto = totalGasto.add(gasto.getValorTotal());

            for (Parcela parcela : gasto.getParcelas()) {
                if (Boolean.TRUE.equals(parcela.getPago())) {
                    totalPago = totalPago.add(parcela.getValor());
                    parcelasPagas++;
                } else {
                    totalPendente = totalPendente.add(parcela.getValor());
                    parcelasPendentes++;
                }
            }
        }

        return new ResumoGastos(totalGasto, totalPago, totalPendente, parcelasPagas, parcelasPendentes);
    }

    public List<ItemAgregadoDTO> materiaisMaisComprados() {
        List<Gasto> todos = repository.findAll();

        Map<String, ItemAgregadoDTO> porDescricao = new LinkedHashMap<>();
        for (Gasto gasto : todos) {
            String chave = gasto.getDescricao().trim().toLowerCase();
            int quantidade = gasto.getQuantidade() != null ? gasto.getQuantidade() : 1;

            ItemAgregadoDTO item = porDescricao.get(chave);
            if (item == null) {
                porDescricao.put(chave, new ItemAgregadoDTO(gasto.getDescricao().trim(), quantidade, gasto.getValorTotal()));
            } else {
                item.setQuantidadeTotal(item.getQuantidadeTotal() + quantidade);
                item.setValorTotalGasto(item.getValorTotalGasto().add(gasto.getValorTotal()));
            }
        }

        return porDescricao.values().stream()
                .sorted(Comparator.comparingInt(ItemAgregadoDTO::getQuantidadeTotal).reversed())
                .collect(Collectors.toList());
    }

    public List<CategoriaGastoDTO> gastoPorCategoria() {
        List<Gasto> todos = repository.findAll();

        Map<CategoriaGasto, BigDecimal> porCategoria = new LinkedHashMap<>();
        for (Gasto gasto : todos) {
            porCategoria.merge(gasto.getCategoria(), gasto.getValorTotal(), BigDecimal::add);
        }

        return porCategoria.entrySet().stream()
                .map(e -> new CategoriaGastoDTO(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(CategoriaGastoDTO::getTotalGasto).reversed())
                .collect(Collectors.toList());
    }

    public record ResumoGastos(BigDecimal totalGasto, BigDecimal totalPago, BigDecimal totalPendente,
                                long parcelasPagas, long parcelasPendentes) {
    }
}
