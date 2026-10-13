package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Categoria;
import com.elwey.restaurante.entities.Comida;
import com.elwey.restaurante.errors.CategoriaNotFoundException;
import com.elwey.restaurante.repository.CategoriaRepository;
import com.elwey.restaurante.repository.ComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    @Autowired
    private ComidaRepository comidaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> seeAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria searchById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    @Override
    @Transactional
    public Categoria guardar(Categoria categoria) {
        if (categoria.getActivo() == null) {
            categoria.setActivo(true);
        }
        if (categoria.getId() != null) {
            repo.findById(categoria.getId()).ifPresent(existente -> {
                if ((categoria.getAdicionales() == null || categoria.getAdicionales().isEmpty())
                        && existente.getAdicionales() != null) {
                    categoria.setAdicionales(existente.getAdicionales());
                }
                if ((categoria.getDescripcion() == null || categoria.getDescripcion().isBlank())
                        && existente.getDescripcion() != null) {
                    categoria.setDescripcion(existente.getDescripcion());
                }
            });
        }
        return repo.save(categoria);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Categoria cat = searchById(id);
        if (cat.getComidas() != null) {
            for (Comida c : cat.getComidas()) {
                c.setCategoria(null);
                comidaRepository.save(c);
            }
        }
        if (cat.getAdicionales() != null) {
            cat.getAdicionales().clear();
            repo.save(cat);
        }
        repo.deleteById(id);
    }
}
