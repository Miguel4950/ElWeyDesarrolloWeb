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

import com.elwey.restaurante.entities.Categoria;
import com.elwey.restaurante.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // http://localhost:8080/categorias/all
    @GetMapping("/all")
    @Operation(summary = "Encuentra todas las categorías del restaurante")
    public ResponseEntity<List<Categoria>> mostrarCategorias() {
        // 1. Consultar todas las categorías registradas
        List<Categoria> lista = categoriaService.seeAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // http://localhost:8080/categorias/1
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una categoría por su ID")
    public ResponseEntity<Categoria> mostrarCategoriaPorId(@PathVariable("id") Long id) {
        // 1. Buscar categoría por identificador
        Categoria categoria = categoriaService.searchById(id);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    // http://localhost:8080/categorias/add
    @PostMapping("/add")
    @Operation(summary = "Crea una nueva categoría de menú")
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria categoria) {
        // 1. Forzar ID nulo para insertar una nueva categoría
        categoria.setId(null);
        Categoria nueva = categoriaService.guardar(categoria);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // http://localhost:8080/categorias/update
    @PutMapping("/update")
    @Operation(summary = "Actualiza una categoría existente")
    public ResponseEntity<Categoria> actualizarCategoria(@RequestBody Categoria categoria) {
        // 1. Verificar que la categoría exista antes de actualizar
        if (categoria.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        categoriaService.searchById(categoria.getId());
        Categoria actualizada = categoriaService.guardar(categoria);
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    // http://localhost:8080/categorias/delete/1
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina una categoría por su ID")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable("id") Long id) {
        // 1. Desvincular comidas asociadas y eliminar la categoría
        categoriaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
