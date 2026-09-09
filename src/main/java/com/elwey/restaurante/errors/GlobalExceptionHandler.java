package com.elwey.restaurante.errors;

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
}
