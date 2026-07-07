package com.obra.obra.services;

import com.obra.obra.dtos.request.CotacaoRequestDTO;
import com.obra.obra.entities.Cotacao;
import com.obra.obra.repositories.CotacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CotacaoService {

    private final CotacaoRepository repository;

    public Cotacao criar(CotacaoRequestDTO dto) {
        Cotacao c = new Cotacao();
        aplicarCampos(c, dto);
        return repository.save(c);
    }

    public List<Cotacao> listarTodos() {
        return repository.findAll();
    }

    public Cotacao atualizar(Long id, CotacaoRequestDTO dto) {
        Cotacao c = buscarPorId(id);
        aplicarCampos(c, dto);
        return repository.save(c);
    }

    @Transactional
    public Cotacao marcarEscolhido(Long id) {
        Cotacao cotacao = buscarPorId(id);
        // desmarca todas da mesma categoria/subcategoria
        List<Cotacao> mesmaSubcategoria = repository.findByCategoriaAndSubcategoria(
                cotacao.getCategoria(), cotacao.getSubcategoria());
        mesmaSubcategoria.forEach(c -> c.setEscolhido(false));
        repository.saveAll(mesmaSubcategoria);

        cotacao.setEscolhido(true);
        return repository.save(cotacao);
    }

    public void deletar(Long id) {
        repository.delete(buscarPorId(id));
    }

    private Cotacao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotação não encontrada"));
    }

    private void aplicarCampos(Cotacao c, CotacaoRequestDTO dto) {
        c.setCategoria(dto.getCategoria());
        c.setSubcategoria(dto.getSubcategoria());
        c.setSubcategoriaCustom(dto.getSubcategoriaCustom());
        c.setItem(dto.getItem());
        c.setFornecedor(dto.getFornecedor());
        c.setValor(dto.getValor());
        c.setQuantidade(dto.getQuantidade());
        c.setUnidade(dto.getUnidade());
        c.setData(dto.getData());
        c.setObservacao(dto.getObservacao());
    }
}
