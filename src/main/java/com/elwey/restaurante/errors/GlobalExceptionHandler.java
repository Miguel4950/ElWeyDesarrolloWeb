package com.elwey.restaurante.errors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ComidaNotFoundException.class)
    public String handleComidaNotFound(ComidaNotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public String handleCategoriaNotFound(CategoriaNotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public String handleClienteNotFound(ClienteNotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public String handlePedidoNotFound(PedidoNotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(AdicionalNotFoundException.class)
    public String handleAdicionalNotFound(AdicionalNotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DomiciliarioNotFoundException.class)
    public String handleDomiciliarioNotFound(DomiciliarioNotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(ComidaEnUsoException.class)
    public String handleComidaEnUso(ComidaEnUsoException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, Model model) {
        model.addAttribute("mensaje", "No es posible eliminar o modificar este registro porque está vinculado a otros registros activos en el sistema del restaurante. Debe gestionar primero las dependencias asociadas.");
        return "error";
    }
}
