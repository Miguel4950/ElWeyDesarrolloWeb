# Restaurante El Wey - Aplicación Web Spring Boot & Thymeleaf (Sprint 3)

Proyecto web desarrollado para la materia **Desarrollo Web**, correspondiente a la entrega del **Sprint 3**, integrando persistencia con **Spring Data JPA**, base de datos en memoria **H2**, manejo global de excepciones con `@ControllerAdvice`, y la relación completa entre entidades.

---

## 🌮 Descripción del Negocio: Restaurante "El Wey"

**El Wey** es un restaurante temático de alta cocina tradicional mexicana. Su propuesta de valor combina recetas ancestrales (tacos al pastor con piña asada al carbón, quesadillas de birria cocinadas a fuego lento, enchiladas rojas en salsa de chiles secos, guacamole en molcajete artesanal) con una experiencia digital moderna.

La aplicación web permite:
1. **A los Comensales y Clientes:**
   - Explorar la carta completa en formato de **galería de tarjetas interactivas** (`/comidas/tarjetas`) o en **tabla detallada** (`/comidas/tabla`).
   - Buscar y filtrar platos por nombre en tiempo real.
   - Consultar la **ficha de detalle individual** de cada platillo (`/comidas/{id}`), donde gracias a la relación JPA se visualiza de forma automática su **categoría asignada**, descripción, acompañamientos incluidos y precio.
   - Crear una cuenta de usuario (`/registro`), iniciar sesión (`/login`) y gestionar su información personal en su portal privado (`/cliente/portal/{id}`).
2. **A los Administradores:**
   - Visualizar el inventario general de comidas y cartas con opciones de edición y eliminación (`/admin/productos`).
   - Crear nuevos platillos asignándoles su categoría mediante un selector dinámico (`/comidas/crear`).
   - Consultar el directorio completo de clientes registrados (`/admin/vertodo`).

---

## 📋 Requisitos del Sprint 3 Cumplidos

| Requisito del Profesor | Implementación en el Proyecto |
| :--- | :--- |
| **1. Tener el Sprint 3 funcionando con JPA** | Integrado `spring-boot-starter-data-jpa` en `pom.xml`, interfaces `JpaRepository<Entidad, Long>`, y eliminación de la falsa base de datos en memoria. |
| **2. Base de datos H2 en memoria** | Configurada en `application.properties` con `jdbc:h2:mem:restaurantdb`, `ddl-auto=create-drop`, logging SQL y consola web habilitada en `/h2`. |
| **3. Notaciones de restricciones en Entidades** | `@Column(nullable = false, unique = true, length = ...)` en `Cliente`, `Comida` y `Categoria`, usando llaves primarias `Long` con `GenerationType.IDENTITY`. |
| **4. Prevención de bucles infinitos en Lombok** | Eliminado `@Data` en entidades relacionadas, utilizando `@Getter`, `@Setter`, `@NoArgsConstructor`, `@Builder` y `@ToString(exclude = ...)`. |
| **5. Relación completa Comida ↔ Categoría** | `@ManyToOne` en `Comida` (dueña de la relación) y `@OneToMany(mappedBy = "categoria")` en `Categoria` con cascada. |
| **6. Detalle de comida mostrando su categoría** | Vista `comida-detalle.html` navegando directamente a `comida.categoria.nombre` mediante el JOIN automático de JPA. |
| **7. Manejo global de errores** | Excepción `ComidaNotFoundException`, manejador `@ControllerAdvice` (`GlobalExceptionHandler`) y vista Thymeleaf `error.html`. |
| **8. Población de datos (DataLoader)** | Componente `DataLoader` (`CommandLineRunner`) con **5 categorías**, **40 comidas** y **10 clientes**. |
| **9. Repositorio y README descriptivo** | Código fuente estructurado y README con descripción detallada del negocio y la arquitectura. |
| **10. Diagrama Entidad-Relación (DER)** | Modelado en Draw.io con cardinalidades (1:N), llaves PK/FK, restricciones (@Column) y especificación técnica. |

---

## 🏛️ Arquitectura del Proyecto

```
com.elwey.restaurante
├── RestauranteApplication.java      // Punto de entrada @SpringBootApplication
├── DataLoader.java                  // Carga de 40 comidas, 5 categorías y 10 clientes en H2
├── entities                         // Entidades JPA con restricciones
│   ├── Categoria.java               // @Entity (id, nombre, descripcion, comidas)
│   ├── Cliente.java                 // @Entity (id, nombre, email, telefono, etc.)
│   └── Comida.java                  // @Entity (id, nombre, precio, descripcion, categoria)
├── repository                       // Interfaces Spring Data JPA
│   ├── CategoriaRepository.java     // JpaRepository<Categoria, Long>
│   ├── ClienteRepository.java       // JpaRepository<Cliente, Long>
│   └── ComidaRepository.java        // JpaRepository<Comida, Long>
├── service                          // Capa de Lógica de Negocio (@Service, @Transactional)
│   ├── CategoriaService.java / CategoriaServiceImpl.java
│   ├── ClienteService.java / ClienteServiceImpl.java
│   └── ComidaService.java / ComidaServiceImpl.java
├── controller                       // Controladores MVC (@Controller)
│   ├── HomeController.java          // /, /login, /registro, /admin
│   ├── ComidaController.java        // /comidas (tarjetas, tabla, detalle, crear, guardar)
│   └── ClienteController.java       // /cliente (portal, actualizar, eliminar)
└── errors                           // Manejo Global de Errores
    ├── ComidaNotFoundException.java     // Excepción para platillos no encontrados
    ├── CategoriaNotFoundException.java  // Excepción para categorías no encontradas
    └── GlobalExceptionHandler.java      // @ControllerAdvice que redirige a error.html
```

---

## 🌐 Mapeo de Rutas Principales

- **`GET /`** : Portada principal del Restaurante El Wey.
- **`GET /comidas/tarjetas`** : Menú completo en formato de tarjetas con filtros y buscador.
- **`GET /comidas/tabla`** : Vista de catálogo en formato de tabla.
- **`GET /comidas/{id}`** : Ficha detallada de un platillo mostrando su categoría.
- **`GET /comidas/crear`** : Formulario con `<select>` dinámico de categorías para registrar un plato.
- **`POST /comidas/guardar`** : Guarda un plato asociándolo a la categoría seleccionada.
- **`GET /h2`** : Consola gráfica de la base de datos en memoria H2.

---

## ⚙️ Conexión a la Consola H2

Cuando la aplicación está en ejecución:
1. Ingresar en el navegador a: `http://localhost:8080/h2`
2. Configurar los campos de conexión:
   - **JDBC URL:** `jdbc:h2:mem:restaurantdb`
   - **User Name:** `sa`
   - **Password:** *(dejar vacío)*
3. Hacer clic en **Connect** para inspeccionar las tablas `COMIDAS`, `CATEGORIAS` y `CLIENTES`.

---

## 🚀 Cómo Ejecutar la Aplicación

1. **Desde IDE (IntelliJ IDEA / Eclipse / VS Code):**
   - Abrir el proyecto.
   - Ejecutar la clase `RestauranteApplication.java`.
   - Abrir en el navegador: `http://localhost:8080`

2. **Desde la Consola / Terminal con Maven:**
   ```bash
   mvn spring-boot:run
   ```
   o usando el Maven Wrapper:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```
