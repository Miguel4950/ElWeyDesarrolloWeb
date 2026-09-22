package com.elwey.restaurante.errors;

public class ComidaEnUsoException extends RuntimeException {
    public ComidaEnUsoException(String mensaje) {
        super(mensaje);
    }

    public ComidaEnUsoException(Long id, String nombre) {
        super("No se puede eliminar el plato '" + nombre + "' (ID: " + id + ") porque forma parte de pedidos registrados en el restaurante. Debe cancelar o eliminar primero dichos pedidos antes de retirar el plato del menú.");
    }
}
