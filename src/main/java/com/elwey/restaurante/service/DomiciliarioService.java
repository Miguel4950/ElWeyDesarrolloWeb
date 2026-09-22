package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Domiciliario;
import java.util.List;

public interface DomiciliarioService {
    List<Domiciliario> searchAll();
    Domiciliario searchById(Long id);
    void save(Domiciliario domiciliario);
    void delete(Long id);
}
