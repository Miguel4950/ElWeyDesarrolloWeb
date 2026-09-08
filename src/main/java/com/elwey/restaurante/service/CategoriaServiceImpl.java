package com.elwey.restaurante.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elwey.restaurante.entities.Categoria;
import com.elwey.restaurante.entities.Comida;
import com.elwey.restaurante.errors.CategoriaNotFoundException;
import com.elwey.restaurante.repository.CategoriaRepository;
import com.elwey.restaurante.repository.ComidaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    @Autowired
    private ComidaRepository comidaRepository;

    Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

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
    @Transactional(readOnly = true)
    public List<Categoria> listarTodos() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public String obtenerNombreCategoriaPorComidaId(Long comidaId) {
        return comidaRepository.findById(comidaId)
                .map(c -> c.getCategoria() != null ? c.getCategoria().getNombre() : "Sin Categoría")
                .orElse("Sin Categoría");
    }

    @Override
    @Transactional
    public void asignarComida(Long categoriaId, Comida comida) {
        Categoria cat = repo.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNotFoundException(categoriaId));
        comida.setCategoria(cat);
        comidaRepository.save(comida);
    }

    @Override
    @Transactional
    public void quitarComida(Long comidaId) {
        comidaRepository.findById(comidaId).ifPresent(c -> {
            c.setCategoria(null);
            comidaRepository.save(c);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Long obtenerIdPorComidaId(Long comidaId) {
        return comidaRepository.findById(comidaId)
                .map(c -> c.getCategoria() != null ? c.getCategoria().getId() : null)
                .orElse(null);
    }
}
