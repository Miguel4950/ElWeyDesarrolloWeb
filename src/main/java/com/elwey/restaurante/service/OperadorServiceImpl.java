package com.elwey.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elwey.restaurante.entities.Operador;
import com.elwey.restaurante.repository.OperadorRepository;

@Service
public class OperadorServiceImpl implements OperadorService {

    @Autowired
    private OperadorRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Operador> searchAll() {
        // 1. Retornar todos los operadores registrados en la base de datos
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Operador searchById(Long id) {
        // 1. Buscar operador por ID o retornar null si no existe
        return repo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Operador guardar(Operador operador) {
        // 1. Si es actualización y la contraseña viene vacía, conservar la actual
        if (operador.getId() != null) {
            repo.findById(operador.getId()).ifPresent(actual -> {
                if (operador.getContrasena() == null || operador.getContrasena().isBlank()) {
                    operador.setContrasena(actual.getContrasena());
                }
            });
        }
        // 2. Guardar operador en el repositorio
        return repo.save(operador);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 1. Eliminar operador por su identificador
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Operador autenticar(String usuario, String contrasena) {
        // 1. Validar que el usuario exista y su contraseña coincida
        if (usuario == null || contrasena == null) {
            throw new IllegalArgumentException("Debe ingresar usuario y contraseña.");
        }
        Operador op = repo.findByUsuario(usuario.trim())
                .orElseThrow(() -> new IllegalArgumentException("No existe un operador con ese usuario."));
        if (!op.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("La contraseña ingresada es incorrecta.");
        }
        return op;
    }
}
