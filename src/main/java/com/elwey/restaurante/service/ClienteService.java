package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Cliente;
import java.util.List;

/**
 * Interfaz de la capa de Servicio de Negocio para Clientes.
 */
public interface ClienteService {

    List<Cliente> listarTodos();

    Cliente obtenerPorId(Long id);

    Cliente obtenerPorEmail(String email);

    Cliente autenticar(String email, String password);

    Cliente guardar(Cliente cliente);

    void eliminar(Long id);
}