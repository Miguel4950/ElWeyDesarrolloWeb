package com.elwey.restaurante.controller;

import java.util.List;
import java.util.Map;

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

import com.elwey.restaurante.entities.Operador;
import com.elwey.restaurante.service.OperadorService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/operadores")
@CrossOrigin(origins = "http://localhost:4200")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;

    // http://localhost:8080/operadores/all
    @GetMapping("/all")
    @Operation(summary = "Encuentra todos los operadores del restaurante")
    public ResponseEntity<List<Operador>> mostrarOperadores() {
        // 1. Consultar listado completo de operadores
        List<Operador> lista = operadorService.searchAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // http://localhost:8080/operadores/1
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un operador por su ID")
    public ResponseEntity<Operador> mostrarOperadorPorId(@PathVariable("id") Long id) {
        // 1. Buscar operador por su identificador
        Operador operador = operadorService.searchById(id);
        if (operador == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(operador, HttpStatus.OK);
    }

    // http://localhost:8080/operadores/login
    @PostMapping("/login")
    @Operation(summary = "Autentica a un operador mediante su usuario y contraseña")
    public ResponseEntity<Operador> iniciarSesionOperador(@RequestBody Map<String, String> credenciales) {
        // 1. Validar credenciales del operador
        String usuario = credenciales.get("usuario");
        String contrasena = credenciales.get("contrasena");
        Operador operador = operadorService.autenticar(usuario, contrasena);
        return new ResponseEntity<>(operador, HttpStatus.OK);
    }

    // http://localhost:8080/operadores/add
    @PostMapping("/add")
    @Operation(summary = "Registra un nuevo operador")
    public ResponseEntity<Operador> agregarOperador(@RequestBody Operador operador) {
        // 1. Forzar ID nulo para crear nuevo operador
        operador.setId(null);
        Operador nuevo = operadorService.guardar(operador);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // http://localhost:8080/operadores/update
    @PutMapping("/update")
    @Operation(summary = "Actualiza un operador existente")
    public ResponseEntity<Operador> actualizarOperador(@RequestBody Operador operador) {
        // 1. Verificar existencia del operador antes de actualizar
        if (operador.getId() == null || operadorService.searchById(operador.getId()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Operador actualizado = operadorService.guardar(operador);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // http://localhost:8080/operadores/delete/1
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina un operador por su ID")
    public ResponseEntity<Void> eliminarOperador(@PathVariable("id") Long id) {
        // 1. Eliminar operador por su identificador
        operadorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
