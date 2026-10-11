package com.elwey.restaurante.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.elwey.restaurante.entities.Comida;
import com.elwey.restaurante.service.ComidaService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/comidas")
@CrossOrigin(origins = "http://localhost:4200")
public class ComidaController {

    @Autowired
    private ComidaService comidaService;

    Logger log = LoggerFactory.getLogger(ComidaController.class);

    // http://localhost:8080/comidas/all
    @GetMapping("/all")
    @Operation(summary = "Encuentra todas las comidas del menú")
    public ResponseEntity<List<Comida>> mostrarComidas() {
        // 1. Obtener listado completo de platos desde el servicio
        List<Comida> lista = comidaService.seeAll();
        // 2. Retornar respuesta HTTP 200 OK con la colección
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // http://localhost:8080/comidas/1
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene el detalle de una comida por su ID")
    public ResponseEntity<Comida> mostrarComidaPorId(@PathVariable("id") Long id) {
        // 1. Consultar el plato por su identificador
        Comida comida = comidaService.searchById(id);
        // 2. Validar existencia y retornar código semántico
        if (comida == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comida, HttpStatus.OK);
    }

    // http://localhost:8080/comidas/buscar?q=taco
    @GetMapping("/buscar")
    @Operation(summary = "Busca comidas cuyo nombre coincida con el término enviado")
    public ResponseEntity<List<Comida>> buscarComidas(
            @RequestParam(name = "q", required = false) String query) {
        // 1. Filtrar comidas por coincidencia parcial en el nombre
        List<Comida> resultados = comidaService.buscarPorNombre(query);
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

    // http://localhost:8080/comidas/add
    @PostMapping("/add")
    @Operation(summary = "Agrega un nuevo plato al menú")
    public ResponseEntity<Comida> agregarComida(
            @RequestBody Comida comida,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId) {
        // 1. Forzar ID nulo para garantizar inserción de un nuevo registro
        comida.setId(null);
        log.info("Agregando nuevo plato: {}", comida.getNombre());
        // 2. Guardar plato con su categoría asociada y retornar 201 CREATED
        Comida nuevaComida = comidaService.guardarComida(comida, categoriaId);
        return new ResponseEntity<>(nuevaComida, HttpStatus.CREATED);
    }

    // http://localhost:8080/comidas/update
    @PutMapping("/update")
    @Operation(summary = "Actualiza los datos de un plato existente")
    public ResponseEntity<Comida> actualizarComida(
            @RequestBody Comida comida,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId) {
        // 1. Verificar que el plato exista antes de actualizar
        if (comida.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        comidaService.searchById(comida.getId());
        // 2. Guardar los cambios y devolver 200 OK con la entidad actualizada
        Comida actualizada = comidaService.guardarComida(comida, categoriaId);
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    // http://localhost:8080/comidas/delete/1
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina un plato del menú por su ID")
    public ResponseEntity<Void> eliminarComida(@PathVariable("id") Long id) {
        // 1. Invocar eliminación en el servicio y retornar 204 NO CONTENT
        log.info("Eliminando comida con ID: {}", id);
        comidaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}