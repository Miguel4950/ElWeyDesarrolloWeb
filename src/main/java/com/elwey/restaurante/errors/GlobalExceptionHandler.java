package com.elwey.restaurante.errors;

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
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        // 1. Devolver estado 404 Not Found cuando no se encuentra la entidad solicitada
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        // 1. Devolver estado 400 Bad Request ante errores de validación o credenciales
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ComidaEnUsoException.class, DataIntegrityViolationException.class })
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        // 1. Devolver estado 409 Conflict cuando un registro tiene dependencias activas
        String msg = ex instanceof ComidaEnUsoException
                ? ex.getMessage()
                : "No es posible eliminar o modificar este registro porque está vinculado a otros registros activos en el sistema.";
        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }
}
