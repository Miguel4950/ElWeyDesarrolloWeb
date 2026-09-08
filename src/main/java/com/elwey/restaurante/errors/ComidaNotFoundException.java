package com.elwey.restaurante.errors;

public class ComidaNotFoundException extends RuntimeException {
    public ComidaNotFoundException(Long id) {
        super("No se pudo encontrar la comida con ID: " + id);
    }
}
