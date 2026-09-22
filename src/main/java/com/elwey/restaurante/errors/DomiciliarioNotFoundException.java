package com.elwey.restaurante.errors;

public class DomiciliarioNotFoundException extends RuntimeException {
    public DomiciliarioNotFoundException(Long id) {
        super("No se pudo encontrar el domiciliario con ID: " + id);
    }
}
