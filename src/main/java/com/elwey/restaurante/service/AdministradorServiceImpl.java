package com.elwey.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elwey.restaurante.entities.Administrador;
import com.elwey.restaurante.repository.AdministradorRepository;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Administrador> searchAll() {
        // 1. Retornar todos los administradores registrados
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Administrador searchById(Long id) {
        // 1. Buscar administrador por ID o retornar null
        return repo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Administrador guardar(Administrador administrador) {
        // 1. Si es edición y la contraseña viene vacía, conservar la actual
        if (administrador.getId() != null) {
            repo.findById(administrador.getId()).ifPresent(actual -> {
                if (administrador.getContrasena() == null || administrador.getContrasena().isBlank()) {
                    administrador.setContrasena(actual.getContrasena());
                }
            });
        }
        // 2. Guardar administrador en base de datos
        return repo.save(administrador);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 1. Eliminar administrador por su identificador
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Administrador autenticar(String usuario, String contrasena) {
        // 1. Verificar que usuario y contraseña correspondan a un administrador registrado
        if (usuario == null || contrasena == null) {
            throw new IllegalArgumentException("Debe ingresar usuario y contraseña.");
        }
        Administrador admin = repo.findByUsuario(usuario.trim())
                .orElseThrow(() -> new IllegalArgumentException("No existe un administrador con ese usuario."));
        if (!admin.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("La contraseña del administrador es incorrecta.");
        }
        return admin;
    }
}
