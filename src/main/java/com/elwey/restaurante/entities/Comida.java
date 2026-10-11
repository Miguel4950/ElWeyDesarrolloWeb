package com.elwey.restaurante.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"categoria"})
public class Comida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(nullable = false, unique = false)
    private Double precio;

    @Column(nullable = true, unique = false, length = 255)
    private String descripcion;

    @Column(name = "imagen_url", nullable = true, unique = false, length = 500)
    private String imagenUrl;

    @Column(nullable = true, unique = false, length = 50)
    private String etiqueta;

    @Column(nullable = false, unique = false)
    @Builder.Default
    private Boolean activo = true;

    // Relación Many-to-One: la comida es dueña de la llave foránea
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false, unique = false)
    private Categoria categoria;

    @JsonIgnore
    public Comida getComida() {
        return this;
    }

    public String getNombreCategoria() {
        return this.categoria != null ? this.categoria.getNombre() : "Sin Categoría";
    }

    public String getImagen_url() {
        return this.imagenUrl;
    }

    public void setImagen_url(String imagen_url) {
        if (imagen_url != null) {
            this.imagenUrl = imagen_url;
        }
    }

    public void setImagenUrl(String imagenUrl) {
        if (imagenUrl != null) {
            this.imagenUrl = imagenUrl;
        }
    }
}
