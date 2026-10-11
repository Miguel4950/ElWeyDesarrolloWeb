package com.elwey.restaurante.service;

import java.util.List;
import com.elwey.restaurante.entities.Operador;

public interface OperadorService {

    List<Operador> searchAll();

    Operador searchById(Long id);

    Operador guardar(Operador operador);

    void delete(Long id);

    Operador autenticar(String usuario, String contrasena);
}
