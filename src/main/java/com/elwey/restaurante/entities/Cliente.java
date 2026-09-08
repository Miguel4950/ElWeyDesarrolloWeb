package com.elwey.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false, length = 70)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = false, length = 20)
    private String telefono;

    @Column(nullable = true, unique = false, length = 100)
    private String password;

    @Column(nullable = true, unique = false, length = 150)
    private String direccion;

    @Column(nullable = true, unique = false, length = 30)
    private String fechaRegistro;

    // Métodos de compatibilidad con vistas y formularios anteriores
    public String getCorreo() {
        return this.email;
    }

    public void setCorreo(String correo) {
        this.email = correo;
    }
}
