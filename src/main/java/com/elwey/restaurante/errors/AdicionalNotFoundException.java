package com.elwey.restaurante.errors;

public class AdicionalNotFoundException extends RuntimeException {
    public AdicionalNotFoundException(Long id) {
        super("No se pudo encontrar el adicional con ID: " + id);
    }
}
