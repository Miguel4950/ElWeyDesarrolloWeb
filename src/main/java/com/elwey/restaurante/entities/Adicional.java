package com.elwey.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"categorias"})
public class Adicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false, length = 80)
    private String nombre;

    @Column(nullable = false, unique = false)
    private Double precio;

    @Column(nullable = false, unique = false)
    @Builder.Default
    private Boolean activo = true;

    @ManyToMany(mappedBy = "adicionales")
    @Builder.Default
    private List<Categoria> categorias = new ArrayList<>();

    // Método de compatibilidad
    public Categoria getCategoria() {
        return (categorias != null && !categorias.isEmpty()) ? categorias.get(0) : null;
    }
}
