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

import com.elwey.restaurante.errors.ComidaEnUsoException;
import com.elwey.restaurante.repository.ItemPedidoRepository;

@Service
public class ComidaServiceImpl implements ComidaService {

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

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
        guardarComida(comida, categoriaId);
    }

    @Override
    @Transactional
    public Comida guardarComida(Comida comida, Long categoriaId) {
        // 1. Resolver el id de la categoria desde el parametro o el objeto anidado
        Long catId = categoriaId;
        if (catId == null && comida.getCategoria() != null && comida.getCategoria().getId() != null) {
            catId = comida.getCategoria().getId();
        }
        if (catId == null) {
            catId = 1L;
        }

        // 2. Buscar la categoria en la BD y asociarla a la comida
        final Long idBusqueda = catId;
        Categoria categoria = categoriaRepository.findById(idBusqueda)
                .orElseThrow(() -> new CategoriaNotFoundException(idBusqueda));
        comida.setCategoria(categoria);

        if (comida.getActivo() == null) {
            comida.setActivo(true);
        }

        // 3. Guardar y retornar la comida persistida
        return comidaRepository.save(comida);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Comida comida = comidaRepository.findById(id)
                .orElseThrow(() -> new ComidaNotFoundException(id));

        if (!itemPedidoRepository.findByComidaId(id).isEmpty()) {
            throw new ComidaEnUsoException(id, comida.getNombre());
        }

        comidaRepository.delete(comida);
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
        String cleanQuery = query.trim();
        return comidaRepository.findByNombreContainingIgnoreCaseOrCategoriaNombreContainingIgnoreCase(cleanQuery, cleanQuery);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        deleteById(id);
    }
}