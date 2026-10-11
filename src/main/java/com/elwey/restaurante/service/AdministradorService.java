package com.elwey.restaurante.service;

import java.util.List;
import com.elwey.restaurante.entities.Administrador;

public interface AdministradorService {

    List<Administrador> searchAll();

    Administrador searchById(Long id);

    Administrador guardar(Administrador administrador);

    void delete(Long id);

    Administrador autenticar(String usuario, String contrasena);
}
