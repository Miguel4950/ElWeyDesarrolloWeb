package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Adicional;
import java.util.List;

public interface AdicionalService {
    List<Adicional> searchAll();
    Adicional searchById(Long id);
    List<Adicional> findByCategoria(Long categoriaId);
    void save(Adicional adicional, Long categoriaId);
    void delete(Long id);
}
