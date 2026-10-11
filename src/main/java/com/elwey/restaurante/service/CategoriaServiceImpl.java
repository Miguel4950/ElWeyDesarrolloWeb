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

    @Override
    @Transactional
    public Categoria guardar(Categoria categoria) {
        // 1. Si es actualización, conservar adicionales y descripción existentes cuando no se envían
        if (categoria.getId() != null) {
            repo.findById(categoria.getId()).ifPresent(existente -> {
                if (categoria.getAdicionales() == null || categoria.getAdicionales().isEmpty()) {
                    categoria.setAdicionales(existente.getAdicionales());
                }
                if (categoria.getDescripcion() == null || categoria.getDescripcion().isBlank()) {
                    categoria.setDescripcion(existente.getDescripcion());
                }
            });
        }
        // 2. Persistir o actualizar la categoría en base de datos
        return repo.save(categoria);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        // 1. Verificar que la categoría exista antes de desvincular y eliminar
        Categoria cat = searchById(id);
        // 2. Desvincular comidas asociadas para evitar violación de llave foránea
        for (Comida c : comidaRepository.findAll()) {
            if (c.getCategoria() != null && id.equals(c.getCategoria().getId())) {
                c.setCategoria(null);
                comidaRepository.save(c);
            }
        }
        // 3. Limpiar relaciones en la tabla intermedia categoria_adicional
        if (cat.getAdicionales() != null && !cat.getAdicionales().isEmpty()) {
            cat.getAdicionales().clear();
            repo.save(cat);
        }
        // 4. Eliminar la categoría por su identificador
        repo.deleteById(id);
    }
}
