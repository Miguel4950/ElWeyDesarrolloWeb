package com.elwey.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "comidas")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(nullable = true, unique = false, length = 255)
    private String descripcion;

    @Column(nullable = true, unique = false)
    private Boolean activo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Builder.Default
    private List<Comida> comidas = new ArrayList<>();
}
