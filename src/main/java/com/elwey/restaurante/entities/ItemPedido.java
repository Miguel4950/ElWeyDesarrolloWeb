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
@ToString(exclude = {"pedido", "comida", "adicionales"})
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private Integer cantidad;

    @Column(nullable = false, unique = false)
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false, unique = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_comida", nullable = false, unique = false)
    private Comida comida;

    @ManyToMany
    @JoinTable(
        name = "item_pedido_adicional",
        joinColumns = @JoinColumn(name = "id_item"),
        inverseJoinColumns = @JoinColumn(name = "id_adicional")
    )
    @Builder.Default
    private List<Adicional> adicionales = new ArrayList<>();
}
