package com.elwey.restaurante.errors;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Long id) {
        super("No se pudo encontrar la categoría con ID: " + id);
    }
}
