package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Adicional;
import com.elwey.restaurante.entities.Categoria;
import com.elwey.restaurante.entities.ItemPedido;
import com.elwey.restaurante.errors.AdicionalNotFoundException;
import com.elwey.restaurante.repository.AdicionalRepository;
import com.elwey.restaurante.repository.CategoriaRepository;
import com.elwey.restaurante.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdicionalServiceImpl implements AdicionalService {

    @Autowired
    private AdicionalRepository adicionalRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Adicional> searchAll() {
        return adicionalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Adicional searchById(Long id) {
        return adicionalRepository.findById(id)
                .orElseThrow(() -> new AdicionalNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Adicional> findByCategoria(Long categoriaId) {
        return adicionalRepository.findByCategoriasId(categoriaId);
    }

    @Override
    @Transactional
    public void save(Adicional adicional, Long categoriaId) {
        guardar(adicional, categoriaId);
    }

    @Override
    @Transactional
    public Adicional guardar(Adicional adicional, Long categoriaId) {
        if (adicional.getActivo() == null) {
            adicional.setActivo(true);
        }
        if (adicional.getId() != null) {
            adicionalRepository.findById(adicional.getId()).ifPresent(existente -> {
                if (categoriaId != null && existente.getCategorias() != null) {
                    for (Categoria catAnterior : new ArrayList<>(existente.getCategorias())) {
                        if (catAnterior.getAdicionales() != null) {
                            catAnterior.getAdicionales().removeIf(
                                    a -> a.getId() != null && a.getId().equals(adicional.getId()));
                            categoriaRepository.save(catAnterior);
                        }
                    }
                    existente.getCategorias().clear();
                } else if (adicional.getCategorias() == null || adicional.getCategorias().isEmpty()) {
                    adicional.setCategorias(existente.getCategorias());
                }
            });
        }
        Adicional guardado = adicionalRepository.save(adicional);
        if (categoriaId != null) {
            Categoria categoria = categoriaService.searchById(categoriaId);
            if (guardado.getCategorias() == null) {
                guardado.setCategorias(new ArrayList<>());
            }
            boolean enAdicional = guardado.getCategorias().stream()
                    .anyMatch(c -> c.getId() != null && c.getId().equals(categoria.getId()));
            if (!enAdicional) {
                guardado.getCategorias().add(categoria);
            }
            if (categoria.getAdicionales() == null) {
                categoria.setAdicionales(new ArrayList<>());
            }
            boolean enCategoria = categoria.getAdicionales().stream()
                    .anyMatch(a -> a.getId() != null && a.getId().equals(guardado.getId()));
            if (!enCategoria) {
                categoria.getAdicionales().add(guardado);
                categoriaRepository.save(categoria);
            }
        }
        return guardado;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Adicional adicional = searchById(id);
        if (adicional.getCategorias() != null) {
            for (Categoria c : adicional.getCategorias()) {
                if (c.getAdicionales() != null) {
                    c.getAdicionales().remove(adicional);
                    categoriaRepository.save(c);
                }
            }
            adicional.getCategorias().clear();
        }
        List<ItemPedido> items = itemPedidoRepository.findAll();
        for (ItemPedido item : items) {
            if (item.getAdicionales() != null && item.getAdicionales().contains(adicional)) {
                item.getAdicionales().remove(adicional);
                itemPedidoRepository.save(item);
            }
        }
        adicionalRepository.delete(adicional);
    }
}
