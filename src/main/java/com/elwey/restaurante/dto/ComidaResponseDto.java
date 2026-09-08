package com.elwey.restaurante.dto;

import com.elwey.restaurante.entities.Comida;

public class ComidaResponseDto {
    private Comida comida;
    private String nombreCategoria;

    // Constructores, Getters y Setters (o Lombok @Data / @AllArgsConstructor)
    public ComidaResponseDto(Comida comida, String nombreCategoria) {
        this.comida = comida;
        this.nombreCategoria = nombreCategoria;
    }

    public Comida getComida() {
        return comida;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }
}
