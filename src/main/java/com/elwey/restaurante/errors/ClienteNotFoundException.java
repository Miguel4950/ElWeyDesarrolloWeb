package com.elwey.restaurante.errors;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(Long id) {
        super("No se pudo encontrar el cliente con ID: " + id);
    }
}
