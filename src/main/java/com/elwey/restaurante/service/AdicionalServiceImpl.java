package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Adicional;
import com.elwey.restaurante.entities.Categoria;
import com.elwey.restaurante.errors.AdicionalNotFoundException;
import com.elwey.restaurante.repository.AdicionalRepository;
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

    @Override
    public List<Adicional> searchAll() {
        return adicionalRepository.findAll();
    }

    @Override
    public Adicional searchById(Long id) {
        return adicionalRepository.findById(id)
                .orElseThrow(() -> new AdicionalNotFoundException(id));
    }

    @Override
    public List<Adicional> findByCategoria(Long categoriaId) {
        return adicionalRepository.findByCategoriasId(categoriaId);
    }

    @Override
    @Transactional
    public void save(Adicional adicional, Long categoriaId) {
        if (categoriaId != null) {
            Categoria categoria = categoriaService.searchById(categoriaId);
            if (adicional.getCategorias() == null) {
                adicional.setCategorias(new ArrayList<>());
            }
            if (!adicional.getCategorias().contains(categoria)) {
                adicional.getCategorias().add(categoria);
            }
            if (categoria.getAdicionales() == null) {
                categoria.setAdicionales(new ArrayList<>());
            }
            if (!categoria.getAdicionales().contains(adicional)) {
                categoria.getAdicionales().add(adicional);
            }
        }
        adicionalRepository.save(adicional);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Adicional adicional = searchById(id);
        if (adicional.getCategorias() != null) {
            for (Categoria c : adicional.getCategorias()) {
                if (c.getAdicionales() != null) {
                    c.getAdicionales().remove(adicional);
                }
            }
            adicional.getCategorias().clear();
        }
        adicionalRepository.delete(adicional);
    }
}
