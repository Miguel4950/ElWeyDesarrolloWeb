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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elwey.restaurante.entities.Adicional;
import com.elwey.restaurante.service.AdicionalService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/adicionales")
@CrossOrigin(origins = "http://localhost:4200")
public class AdicionalController {

    @Autowired
    private AdicionalService adicionalService;

    // http://localhost:8080/adicionales/all
    @GetMapping("/all")
    @Operation(summary = "Obtiene todos los adicionales del restaurante")
    public ResponseEntity<List<Adicional>> mostrarAdicionales() {
        // 1. Consultar catálogo completo de adicionales
        List<Adicional> lista = adicionalService.searchAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // http://localhost:8080/adicionales/1 o http://localhost:8080/adicionales/find/1
    @GetMapping({"/{id}", "/find/{id}"})
    @Operation(summary = "Obtiene un adicional por su ID")
    public ResponseEntity<Adicional> mostrarAdicionalPorId(@PathVariable("id") Long id) {
        // 1. Buscar adicional por identificador
        Adicional adicional = adicionalService.searchById(id);
        return new ResponseEntity<>(adicional, HttpStatus.OK);
    }

    // http://localhost:8080/adicionales/categoria/1
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Obtiene los adicionales disponibles para una categoría específica")
    public ResponseEntity<List<Adicional>> mostrarAdicionalesPorCategoria(
            @PathVariable("categoriaId") Long categoriaId) {
        // 1. Filtrar adicionales compatibles con la categoría indicada
        List<Adicional> lista = adicionalService.findByCategoria(categoriaId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // http://localhost:8080/adicionales/add
    @PostMapping("/add")
    @Operation(summary = "Crea un nuevo adicional en el catálogo")
    public ResponseEntity<Adicional> agregarAdicional(
            @RequestBody Adicional adicional,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId) {
        // 1. Forzar ID nulo para insertar un nuevo registro
        adicional.setId(null);
        Adicional nuevo = adicionalService.guardar(adicional, categoriaId);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // http://localhost:8080/adicionales/update
    @PutMapping("/update")
    @Operation(summary = "Actualiza un adicional existente")
    public ResponseEntity<Adicional> actualizarAdicional(
            @RequestBody Adicional adicional,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId) {
        // 1. Verificar existencia del adicional antes de guardar cambios
        if (adicional.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        adicionalService.searchById(adicional.getId());
        Adicional actualizado = adicionalService.guardar(adicional, categoriaId);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // http://localhost:8080/adicionales/delete/1
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina un adicional por su ID")
    public ResponseEntity<Void> eliminarAdicional(@PathVariable("id") Long id) {
        // 1. Eliminar adicional desvinculando relaciones previas
        adicionalService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
