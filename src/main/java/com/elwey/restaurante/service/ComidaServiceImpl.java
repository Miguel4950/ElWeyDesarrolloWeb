package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Categoria;
import com.elwey.restaurante.entities.Comida;
import com.elwey.restaurante.errors.CategoriaNotFoundException;
import com.elwey.restaurante.errors.ComidaNotFoundException;
import com.elwey.restaurante.repository.CategoriaRepository;
import com.elwey.restaurante.repository.ComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComidaServiceImpl implements ComidaService {

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comida> seeAll() {
        return comidaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Comida searchById(Long id) {
        return comidaRepository.findById(id)
                .orElseThrow(() -> new ComidaNotFoundException(id));
    }

    @Override
    @Transactional
    public void save(Comida comida, Long categoriaId) {
        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new CategoriaNotFoundException(categoriaId));
            comida.setCategoria(categoria);
        }
        comidaRepository.save(comida);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        comidaRepository.deleteById(id);
    }

    // Métodos de compatibilidad con vistas existentes
    @Override
    @Transactional(readOnly = true)
    public List<Comida> listarTodas() {
        return seeAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comida> listarTodasConCategoria() {
        return seeAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Comida obtenerPorId(Long id) {
        return searchById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Comida obtenerEntidadPorId(Long id) {
        return searchById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comida> buscarPorNombre(String query) {
        if (query == null || query.trim().isEmpty()) {
            return seeAll();
        }
        return comidaRepository.findByNombre(query.trim());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        deleteById(id);
    }
}