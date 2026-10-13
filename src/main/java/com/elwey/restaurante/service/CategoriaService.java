package com.elwey.restaurante.service;

import java.util.List;
import com.elwey.restaurante.entities.Categoria;

public interface CategoriaService {

    List<Categoria> seeAll();

    Categoria searchById(Long id);

    Categoria guardar(Categoria categoria);

    void eliminar(Long id);
}
