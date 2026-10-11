package com.elwey.restaurante.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "adicionales")
    @Builder.Default
    private List<Categoria> categorias = new ArrayList<>();

    @JsonIgnore
    public Categoria getCategoria() {
        return (categorias != null && !categorias.isEmpty()) ? categorias.get(0) : null;
    }

    public String getNombresCategorias() {
        if (categorias == null || categorias.isEmpty()) {
            return "General";
        }
        List<String> nombres = new ArrayList<>();
        for (Categoria c : categorias) {
            if (c != null && c.getNombre() != null) {
                nombres.add(c.getNombre());
            }
        }
        return nombres.isEmpty() ? "General" : String.join(", ", nombres);
    }

    public Long getCategoriaId() {
        return (categorias != null && !categorias.isEmpty() && categorias.get(0) != null)
                ? categorias.get(0).getId()
                : 1L;
    }
}
