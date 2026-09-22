package com.elwey.restaurante.errors;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(Long id) {
        super("No se pudo encontrar el pedido con ID: " + id);
    }
}
