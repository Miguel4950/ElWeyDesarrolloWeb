package com.elwey.restaurante.dto;

import com.elwey.restaurante.entities.Comida;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ComidaResponseDto {
    private Comida comida;
    private String nombreCategoria;
}
