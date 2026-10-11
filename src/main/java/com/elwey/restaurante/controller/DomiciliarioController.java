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

import com.elwey.restaurante.entities.Domiciliario;
import com.elwey.restaurante.service.DomiciliarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/domiciliarios")
@CrossOrigin(origins = "http://localhost:4200")
public class DomiciliarioController {

    @Autowired
    private DomiciliarioService domiciliarioService;

    // http://localhost:8080/domiciliarios/all
    @GetMapping("/all")
    @Operation(summary = "Encuentra todos los domiciliarios del restaurante")
    public ResponseEntity<List<Domiciliario>> mostrarDomiciliarios() {
        // 1. Consultar listado completo de domiciliarios
        List<Domiciliario> lista = domiciliarioService.searchAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // http://localhost:8080/domiciliarios/1 o http://localhost:8080/domiciliarios/find/1
    @GetMapping({"/{id}", "/find/{id}"})
    @Operation(summary = "Obtiene un domiciliario por su ID")
    public ResponseEntity<Domiciliario> mostrarDomiciliarioPorId(@PathVariable("id") Long id) {
        // 1. Buscar domiciliario por su identificador
        Domiciliario domiciliario = domiciliarioService.searchById(id);
        return new ResponseEntity<>(domiciliario, HttpStatus.OK);
    }

    // http://localhost:8080/domiciliarios/add
    @PostMapping("/add")
    @Operation(summary = "Registra un nuevo domiciliario")
    public ResponseEntity<Domiciliario> agregarDomiciliario(@RequestBody Domiciliario domiciliario) {
        // 1. Forzar ID nulo para insertar nuevo domiciliario
        domiciliario.setId(null);
        Domiciliario nuevo = domiciliarioService.guardar(domiciliario);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // http://localhost:8080/domiciliarios/update
    @PutMapping("/update")
    @Operation(summary = "Actualiza los datos o disponibilidad de un domiciliario")
    public ResponseEntity<Domiciliario> actualizarDomiciliario(@RequestBody Domiciliario domiciliario) {
        // 1. Verificar que el domiciliario exista antes de actualizar
        if (domiciliario.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        domiciliarioService.searchById(domiciliario.getId());
        Domiciliario actualizado = domiciliarioService.guardar(domiciliario);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // http://localhost:8080/domiciliarios/delete/1
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina un domiciliario por su ID")
    public ResponseEntity<Void> eliminarDomiciliario(@PathVariable("id") Long id) {
        // 1. Desvincular pedidos asociados y eliminar domiciliario
        domiciliarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
