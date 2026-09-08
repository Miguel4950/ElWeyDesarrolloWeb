package com.elwey.restaurante;

import com.elwey.restaurante.entities.Categoria;
import com.elwey.restaurante.entities.Cliente;
import com.elwey.restaurante.entities.Comida;
import com.elwey.restaurante.repository.CategoriaRepository;
import com.elwey.restaurante.repository.ClienteRepository;
import com.elwey.restaurante.repository.ComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {

        // 1. Crear 5 Categorías representativas del restaurante
        String[] nombresCategorias = { "Tacos", "Quesadillas", "Entradas", "Especialidades", "Burritos" };
        String[] descCategorias = {
                "Variedad de tacos tradicionales servidos en tortilla de maíz o harina con ingredientes frescos.",
                "Muestras de queso fundido e ingredientes variados en tortillas a la plancha.",
                "Platos ideales para comenzar la experiencia o compartir en la mesa.",
                "Platos insignia del restaurante preparados con recetas exclusivas de la casa.",
                "Tortillas de harina gigantes rellenas de carne, frijoles, arroz y complementos."
        };

        List<Categoria> categorias = new ArrayList<>();
        for (int i = 0; i < nombresCategorias.length; i++) {
            Categoria c = Categoria.builder()
                    .nombre(nombresCategorias[i])
                    .descripcion(descCategorias[i])
                    .activo(true)
                    .build();
            categorias.add(categoriaRepository.save(c));
        }

        Categoria catTacos = categorias.get(0);
        Categoria catQuesadillas = categorias.get(1);
        Categoria catEntradas = categorias.get(2);
        Categoria catEspecialidades = categorias.get(3);
        Categoria catBurritos = categorias.get(4);

        // 2. Crear 40 Comidas auténticas (los 8 originales del Mockup + 32 platos adicionales)
        List<Comida> comidas = new ArrayList<>();

        // Los 8 platos emblemáticos del Sprint 1 y 2
        comidas.add(new Comida(null, "Tacos al Pastor", 18000.0,
                "Carne de cerdo marinada en achiote tradicional, piña asada al carbón, cilantro fresco y cebolla en tortilla de maíz nixtamalizada.",
                "https://images.unsplash.com/photo-1648437595587-e6a8b0cdf1f9?w=600&h=400&fit=crop&auto=format",
                "Estrella", true, catTacos));

        comidas.add(new Comida(null, "Quesadilla de Birria", 22000.0,
                "Tortilla de maíz rellena de queso Oaxaca fundido y jugosa birria de res cocinada a fuego lento por 8 horas. Acompañada de consomé para sumergir.",
                "https://images.unsplash.com/photo-1617990590988-895fe6cbabda?w=600&h=400&fit=crop&auto=format",
                "Top Ventas", true, catQuesadillas));

        comidas.add(new Comida(null, "Guacamole El Wey", 15000.0,
                "Aguacate Hass seleccionado machacado al momento en molcajete con tomate, jalapeño, cilantro y toque de limón. Servido con totopos artesanales crujientes.",
                "https://images.unsplash.com/photo-1613514967307-d5b3471b2453?w=600&h=400&fit=crop&auto=format",
                "Vegano", true, catEntradas));

        comidas.add(new Comida(null, "Enchiladas Rojas", 20000.0,
                "Tres tortillas de maíz rellenas de pechuga de pollo deshebrada, bañadas en salsa roja de chiles secos, cubiertas con crema ácida, queso fresco y cebolla morada.",
                "https://images.unsplash.com/photo-1670213545333-9d4ec81de4b1?w=600&h=400&fit=crop&auto=format",
                "Recomendado", true, catEspecialidades));

        comidas.add(new Comida(null, "Burrito Norteño", 24000.0,
                "Tortilla de harina gigante rellena de carne asada al carbón, frijoles refritos bayos, arroz a la mexicana, queso Chihuahua fundido y pico de gallo.",
                "https://images.unsplash.com/photo-1626200419199-391ae4be7a41?w=600&h=400&fit=crop&auto=format",
                "Grande", true, catBurritos));

        comidas.add(new Comida(null, "Tacos de Suadero", 19000.0,
                "Corte suave de res confitado lentamente en su propia grasa estilo CDMX, picado al momento con cilantro criollo, cebolla y salsa taquera verde.",
                "https://images.unsplash.com/photo-1599974579688-8dbdd335c77f?w=600&h=400&fit=crop&auto=format",
                "Clásico", true, catTacos));

        comidas.add(new Comida(null, "Nachos El Wey", 23000.0,
                "Cama abundante de totopos de maíz crujientes, frijoles refritos, abundante queso fundido Oaxaca y Cheddar, pico de gallo fresco, jalapeños en escabeche, crema ácida y guacamole especial.",
                "https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?w=600&h=400&fit=crop&auto=format",
                "Para Compartir", true, catEntradas));

        comidas.add(new Comida(null, "Gringa con Carne (la favorita de jaimes)", 21000.0,
                "Deliciosa quesadilla en tortilla de harina dorada a la plancha con mantequilla, rellena de abundante carne al pastor, queso Oaxaca derretido, piña asada caramelizada y cilantro fresco.",
                "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=600&h=400&fit=crop&auto=format",
                "Favorito", true, catQuesadillas));

        // Platos 9 al 40 (32 platillos mexicanos adicionales)
        String[] nombresExtra = {
                "Tacos de Carnitas Michoacán", "Tacos de Pescado Baja", "Tacos Gobernador", "Tacos de Cochinita Pibil",
                "Tacos de Barbacoa Hidalguense", "Tacos de Rib Eye con Tuétano", "Tacos de Chicharrón en Salsa Verde", "Tacos de Asada Poblana",
                "Quesadilla de Flor de Calabaza", "Quesadilla de Champiñones al Ajillo", "Quesadilla Sincronizada", "Quesadilla de Chicharrón Prensado",
                "Quesadilla Norteña con Arrachera", "Quesadilla de Tinga Poblana", "Totopos con Queso Fundido y Rajas", "Esquites Callejeros con Epazote",
                "Queso Fundido con Chorizo Casero", "Flautas Doradas de Pollo", "Sopes Tradicionales con Cecina", "Tostadas de Tinga con Crema",
                "Chiles en Nogada de Puebla", "Pozole Rojo Tradicional", "Mole Poblano con Pollo", "Tamal Oaxaqueño en Hoja de Plátano",
                "Fajitas Mixtas al Carbón", "Cochinita Pibil en Plato Hondo", "Birria Tapatía de Res en Cazuela", "Alambre Mixto de Res y Tocino",
                "Burrito California con Papas", "Burrito de Tinga Deshebrada", "Burrito al Pastor con Queso", "Burrito Vegetariano con Frijol Negro"
        };

        Double[] preciosExtra = {
                19500.0, 23000.0, 25000.0, 20000.0,
                22500.0, 28000.0, 18500.0, 21000.0,
                16000.0, 17000.0, 18000.0, 19000.0,
                24000.0, 19500.0, 14000.0, 12000.0,
                19000.0, 17500.0, 18000.0, 16500.0,
                32000.0, 26000.0, 27000.0, 14000.0,
                29000.0, 26000.0, 27500.0, 28000.0,
                25000.0, 22000.0, 23500.0, 20000.0
        };

        String[] fotosExtra = {
                "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1512838243191-0b10497ed592?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1648437595587-e6a8b0cdf1f9?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1617990590988-895fe6cbabda?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1599974579688-8dbdd335c77f?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1617990590988-895fe6cbabda?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1617990590988-895fe6cbabda?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1617990590988-895fe6cbabda?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1613514967307-d5b3471b2453?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1599974579688-8dbdd335c77f?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1613514967307-d5b3471b2453?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1670213545333-9d4ec81de4b1?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1670213545333-9d4ec81de4b1?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1670213545333-9d4ec81de4b1?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1670213545333-9d4ec81de4b1?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1626200419199-391ae4be7a41?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1670213545333-9d4ec81de4b1?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1617990590988-895fe6cbabda?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1626200419199-391ae4be7a41?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1626200419199-391ae4be7a41?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1626200419199-391ae4be7a41?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1626200419199-391ae4be7a41?w=600&h=400&fit=crop&auto=format",
                "https://images.unsplash.com/photo-1626200419199-391ae4be7a41?w=600&h=400&fit=crop&auto=format"
        };

        Categoria[] catAsignadaExtra = {
                catTacos, catTacos, catTacos, catTacos,
                catTacos, catTacos, catTacos, catTacos,
                catQuesadillas, catQuesadillas, catQuesadillas, catQuesadillas,
                catQuesadillas, catQuesadillas, catEntradas, catEntradas,
                catEntradas, catEntradas, catEntradas, catEntradas,
                catEspecialidades, catEspecialidades, catEspecialidades, catEspecialidades,
                catEspecialidades, catEspecialidades, catEspecialidades, catEspecialidades,
                catBurritos, catBurritos, catBurritos, catBurritos
        };

        String[] tagsExtra = {
                "Tradicional", "Costeño", "Mariscos", "Yucateco",
                "Campestre", "Gourmet", "Picante", "Casero",
                "Vegetariano", "Ligero", "Especial", "Frito",
                "Parrilla", "Sabor Único", "Crujiente", "Callejero",
                "Queso", "Dorado", "Rústico", "Fresco",
                "Poblano", "Patrio", "Artesanal", "Del Sur",
                "Al Carbón", "Horneado", "Caliente", "Abundante",
                "Estilo USA", "Deshebrada", "Pastor", "Saludable"
        };

        for (int i = 0; i < nombresExtra.length; i++) {
            comidas.add(new Comida(null, nombresExtra[i], preciosExtra[i],
                    "Auténtico plato de " + nombresExtra[i] + " elaborado con ingredientes de la más alta calidad mexicana.",
                    fotosExtra[i], tagsExtra[i], true, catAsignadaExtra[i]));
        }

        for (Comida c : comidas) {
            comidaRepository.save(c);
        }

        // 3. Crear 10 Clientes (Jugadores de fútbol famosos con emails únicos)
        String[] nombresClientes = {
                "Lionel Messi", "Cristiano Ronaldo", "Radamel Falcao", "James Rodríguez", "Luis Díaz",
                "Neymar Jr", "Kylian Mbappé", "Luka Modrić", "Erling Haaland", "Zinedine Zidane"
        };
        String[] emailsClientes = {
                "lionel.messi@email.com", "cristiano.ronaldo@email.com", "falcao.garcia@email.com", "james.rodriguez@email.com", "luis.diaz@email.com",
                "neymar.jr@email.com", "kylian.mbappe@email.com", "luka.modric@email.com", "erling.haaland@email.com", "zinedine.zidane@email.com"
        };
        String[] passwords = {
                "messi10", "cr7cr7", "falcao9", "james10", "lucho7",
                "ney11", "mbappe9", "modric10", "haaland9", "zizou5"
        };
        String[] telefonos = {
                "+57 300 101 0101", "+57 311 777 0707", "+57 320 999 0909", "+57 301 101 1010", "+57 315 707 0707",
                "+57 318 111 1111", "+57 312 909 0909", "+57 310 101 0100", "+57 314 909 9090", "+57 317 505 0505"
        };
        String[] direcciones = {
                "Avenida Rosarina 10, Rosario", "Quinta da Madeira 7, Lisboa", "Carrera 30 #57-60 El Campín, Bogotá", "Calle 10 #10-10, Envigado", "Calle Principal #7, Barrancas Guajira",
                "Avenida Santos 11, São Paulo", "Paseo de la Castellana 9, Madrid", "Calle Zadar 10, Madrid", "Etihad Campus Way 9, Mánchester", "Boulevard Marsella 5, Madrid"
        };

        for (int i = 0; i < 10; i++) {
            Cliente cliente = Cliente.builder()
                    .nombre(nombresClientes[i])
                    .email(emailsClientes[i])
                    .password(passwords[i])
                    .telefono(telefonos[i])
                    .direccion(direcciones[i])
                    .fechaRegistro("2026-0" + ((i % 3) + 1) + "-15")
                    .build();
            clienteRepository.save(cliente);
        }
    }
}
