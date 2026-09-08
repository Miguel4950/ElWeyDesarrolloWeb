package com.elwey.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString(exclude = "categoria")
public class Comida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false, length = 80)
    private String nombre;

    @Column(nullable = false, unique = false)
    private Double precio;

    @Column(nullable = true, unique = false, length = 255)
    private String descripcion;

    @Column(nullable = true, unique = false, length = 500)
    private String imagenUrl;

    @Column(nullable = true, unique = false, length = 50)
    private String etiqueta;

    @Column(nullable = true, unique = false)
    private Boolean activo;

    // Relación Many-to-One: la comida es dueña de la llave foránea
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false, unique = false)
    private Categoria categoria;

    // Constructor exacto que pide el profesor: todos los campos MENOS el id
    public Comida(String nombre, Double precio, String descripcion, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    // Constructor completo para que @Builder funcione sin chillar
    public Comida(Long id, String nombre, Double precio, String descripcion, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    // Constructor con campos visuales sin ID
    public Comida(String nombre, Double precio, String descripcion, String imagenUrl, String etiqueta, Boolean activo, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.etiqueta = etiqueta;
        this.activo = activo != null ? activo : true;
        this.categoria = categoria;
    }

    // Constructor completo con campos visuales
    public Comida(Long id, String nombre, Double precio, String descripcion, String imagenUrl, String etiqueta, Boolean activo, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.etiqueta = etiqueta;
        this.activo = activo != null ? activo : true;
        this.categoria = categoria;
    }

    // Métodos de compatibilidad con las vistas de plantillas
    public Comida getComida() {
        return this;
    }

    public String getNombreCategoria() {
        return this.categoria != null ? this.categoria.getNombre() : "Sin Categoría";
    }
}
