package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Comida;
import java.util.List;

public interface ComidaService {

    // Métodos exactos de la clase del profesor
    List<Comida> seeAll();

    Comida searchById(Long id);

    void save(Comida comida, Long categoriaId);

    void deleteById(Long id);

    // Métodos de compatibilidad con las vistas existentes
    List<Comida> listarTodas();

    List<Comida> listarTodasConCategoria();

    Comida obtenerPorId(Long id);

    Comida obtenerEntidadPorId(Long id);

    List<Comida> buscarPorNombre(String query);

    void eliminar(Long id);
}
