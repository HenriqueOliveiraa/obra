package com.obra.obra.services;

import com.obra.obra.dtos.request.ItemCompraRequestDTO;
import com.obra.obra.entities.ItemCompra;
import com.obra.obra.repositories.ItemCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaComprasService {

    private final ItemCompraRepository repository;

    public ItemCompra criar(ItemCompraRequestDTO dto) {
        ItemCompra item = new ItemCompra();
        aplicarCampos(item, dto);
        return repository.save(item);
    }

    public List<ItemCompra> listarTodos() {
        return repository.findAll();
    }

    public ItemCompra atualizar(Long id, ItemCompraRequestDTO dto) {
        ItemCompra item = buscarPorId(id);
        aplicarCampos(item, dto);
        return repository.save(item);
    }

    public ItemCompra marcarComprado(Long id) {
        ItemCompra item = buscarPorId(id);
        item.setComprado(true);
        return repository.save(item);
    }

    public void deletar(Long id) {
        repository.delete(buscarPorId(id));
    }

    private ItemCompra buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de compra não encontrado"));
    }

    private void aplicarCampos(ItemCompra item, ItemCompraRequestDTO dto) {
        item.setDescricao(dto.getDescricao());
        item.setCategoria(dto.getCategoria());
        item.setQuantidadeDesejada(dto.getQuantidadeDesejada());
        item.setPrioridade(dto.getPrioridade());
        item.setObservacao(dto.getObservacao());
        item.setComprado(dto.getComprado() != null ? dto.getComprado() : false);
    }
}
