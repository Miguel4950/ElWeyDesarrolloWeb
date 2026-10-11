package com.elwey.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elwey.restaurante.entities.Administrador;
import com.elwey.restaurante.service.AdministradorService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/administradores")
@CrossOrigin(origins = "http://localhost:4200")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    // http://localhost:8080/administradores/all
    @GetMapping("/all")
    @Operation(summary = "Encuentra todos los administradores del sistema")
    public ResponseEntity<List<Administrador>> mostrarAdministradores() {
        // 1. Consultar listado completo de administradores
        List<Administrador> lista = administradorService.searchAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // http://localhost:8080/administradores/1 o http://localhost:8080/administradores/find/1
    @GetMapping({"/{id}", "/find/{id}"})
    @Operation(summary = "Obtiene un administrador por su ID")
    public ResponseEntity<Administrador> mostrarAdministradorPorId(@PathVariable("id") Long id) {
        // 1. Buscar administrador por identificador
        Administrador admin = administradorService.searchById(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    // http://localhost:8080/administradores/login
    @PostMapping("/login")
    @Operation(summary = "Autentica a un administrador mediante usuario y contraseña")
    public ResponseEntity<Administrador> iniciarSesionAdmin(@RequestBody Administrador credenciales) {
        // 1. Extraer credenciales y validar contra el servicio de administradores
        String usuario = credenciales.getUsuario();
        String contrasena = credenciales.getContrasena();
        Administrador admin = administradorService.autenticar(usuario, contrasena);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    // http://localhost:8080/administradores/add
    @PostMapping("/add")
    @Operation(summary = "Registra un nuevo administrador")
    public ResponseEntity<Administrador> agregarAdministrador(@RequestBody Administrador administrador) {
        // 1. Forzar ID nulo para insertar nuevo registro
        administrador.setId(null);
        Administrador nuevo = administradorService.guardar(administrador);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // http://localhost:8080/administradores/update
    @PutMapping("/update")
    @Operation(summary = "Actualiza los datos de un administrador existente")
    public ResponseEntity<Administrador> actualizarAdministrador(@RequestBody Administrador administrador) {
        // 1. Verificar existencia del administrador antes de actualizar
        if (administrador.getId() == null || administradorService.searchById(administrador.getId()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Administrador actualizado = administradorService.guardar(administrador);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // http://localhost:8080/administradores/delete/1
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina un administrador por su ID")
    public ResponseEntity<Void> eliminarAdministrador(@PathVariable("id") Long id) {
        // 1. Eliminar administrador por su identificador
        administradorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
