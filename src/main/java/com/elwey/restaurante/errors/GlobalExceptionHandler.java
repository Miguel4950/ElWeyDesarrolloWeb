package com.elwey.restaurante.errors;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ComidaNotFoundException.class,
            CategoriaNotFoundException.class,
            ClienteNotFoundException.class,
            PedidoNotFoundException.class,
            AdicionalNotFoundException.class,
            DomiciliarioNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
        // 1. Devolver estado 404 Not Found cuando no se encuentra la entidad solicitada
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        // 1. Devolver estado 400 Bad Request ante errores de validación o credenciales
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensaje", ex.getMessage()));
    }

    @ExceptionHandler({ ComidaEnUsoException.class, DataIntegrityViolationException.class })
    public ResponseEntity<Map<String, String>> handleConflict(RuntimeException ex) {
        // 1. Devolver estado 409 Conflict cuando un registro tiene dependencias activas
        String msg = ex instanceof ComidaEnUsoException
                ? ex.getMessage()
                : "No es posible eliminar o modificar este registro porque está vinculado a otros registros activos en el sistema.";
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("mensaje", msg));
    }
}
