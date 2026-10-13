package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Comida;
import java.util.List;

public interface ComidaService {

    List<Comida> seeAll();

    Comida searchById(Long id);

    Comida guardarComida(Comida comida, Long categoriaId);

    void deleteById(Long id);

    List<Comida> buscarPorNombre(String query);

    List<Comida> listarTodas();

    Comida obtenerPorId(Long id);
}
