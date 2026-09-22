# Restaurante El Wey - Aplicación Web Spring Boot & Thymeleaf (Sprint 4)

Proyecto web desarrollado para la materia **Desarrollo Web**, correspondiente a la entrega del **Sprint 4**, integrando persistencia con **Spring Data JPA**, base de datos en memoria **H2**, manejo global de excepciones con `@ControllerAdvice`, el módulo completo de Pedidos con Domiciliarios, Adicionales, Operadores y Administradores, y la relación relacional completa entre entidades.

---

## 🌮 Descripción del Negocio: Restaurante "El Wey"

**El Wey** es un restaurante temático de alta cocina tradicional mexicana. Su propuesta de valor combina recetas ancestrales (tacos al pastor con piña asada al carbón, quesadillas de birria cocinadas a fuego lento, enchiladas rojas en salsa de chiles secos, guacamole en molcajete artesanal) con una experiencia digital moderna.

La aplicación web permite:
1. **A los Comensales y Clientes:**
   - Explorar la carta completa en formato de **galería de tarjetas interactivas** (`/comidas/tarjetas`) o en **tabla detallada** (`/comidas/tabla`).
   - Buscar y filtrar platos por categoría y nombre en tiempo real.
   - Consultar la **ficha de detalle individual** de cada platillo (`/comidas/{id}`), visualizando su categoría asignada, acompañamientos, ingredientes y precio.
   - Crear una cuenta de usuario (`/registro`), iniciar sesión (`/login`) y gestionar su información personal en su portal privado (`/cliente/portal/{id}`).
   - Visualizar el historial y estado de pedidos.
2. **A los Operadores y Administradores:**
   - Visualizar el inventario general de comidas y cartas con opciones de edición y eliminación (`/admin/productos`).
   - Crear nuevos platillos asignándoles su categoría y adicionales (`/comidas/crear`).
   - Gestionar los pedidos activos, asignar domiciliarios y cambiar estados del despacho (`/pedidos`, `/pedidos/{id}`).
   - Consultar el directorio completo de clientes registrados (`/admin/vertodo`).

---

## 📋 Requisitos y Funcionalidades del Sprint 4

| Componente / Requisito | Implementación en el Proyecto |
| :--- | :--- |
| **1. Persistencia JPA y H2** | Entidades JPA completas mapeadas a tablas H2 con llaves primarias, foráneas e índices. |
| **2. Modelo Relacional Extendido** | Entidades `Pedido`, `ItemPedido`, `Domiciliario`, `Adicional`, `Operador`, `Administrador`, `Cliente`, `Comida`, `Categoria`. |
| **3. Gestión de Pedidos** | Control y visualización de órdenes de compra con cálculo de subtotales, totales y asignación de repartidor. |
| **4. Relaciones N:M y 1:N** | Relaciones completas: Cliente ↔ Pedidos (1:N), Pedido ↔ Items (1:N), Pedido ↔ Domiciliario (N:1), Comida ↔ Categoría (N:1), Comida/Item ↔ Adicionales. |
| **5. Manejo Global de Errores** | `@ControllerAdvice` (`GlobalExceptionHandler`) con excepciones específicas (`PedidoNotFoundException`, `DomiciliarioNotFoundException`, `AdicionalNotFoundException`, `ComidaEnUsoException`, `ComidaNotFoundException`). |
| **6. Población de Datos Inicial (DataLoader)** | Componente `CommandLineRunner` que precarga categorías, comidas, clientes, domiciliarios, adicionales, operadores, administradores y pedidos con items. |
| **7. Diagramas de Diseño** | Diagrama de Clases UML y Diagramas Entidad-Relación (DER) disponibles en la carpeta `Diagramas/`. |

---

## 📐 Diagramas del Sistema

Los diagramas arquitectónicos y relacionales del sistema se encuentran en el directorio [`Diagramas/`](file:///c:/Users/migue/Desktop/ElWeyDesarrolloWeb-main-V2/Diagramas):
- **Diagrama de Clases UML:** `Diagramas/DiagramaDeClases.png`
- **Diagrama Entidad-Relación (DER Físico):** `Diagramas/DiagramaER.png`
- **Diagrama Entidad-Relación Relacional:** `Diagramas/Untitled (1) (1).png`

---

## 🏛️ Arquitectura del Proyecto

```
com.elwey.restaurante
├── RestauranteApplication.java      // Punto de entrada @SpringBootApplication
├── DataLoader.java                  // Carga inicial de datos en H2 (Comidas, Clientes, Pedidos, Domiciliarios, etc.)
├── entities                         // Entidades JPA con restricciones
│   ├── Adicional.java               // @Entity (id, nombre, precio, activo)
│   ├── Administrador.java           // @Entity (idAdmin, usuario, contrasena)
│   ├── Categoria.java               // @Entity (id, nombre, descripcion, comidas)
│   ├── Cliente.java                 // @Entity (id, nombre, apellido, correo, telefono, direccion, pedidos)
│   ├── Comida.java                  // @Entity (id, nombre, precio, descripcion, categoria, activo)
│   ├── Domiciliario.java            // @Entity (id, cedula, nombre, celular, disponible)
│   ├── ItemPedido.java              // @Entity (id, pedido, comida, cantidad, subtotal, adicionales)
│   ├── Operador.java                // @Entity (idOperador, nombre, usuario, contrasena)
│   └── Pedido.java                  // @Entity (id, cliente, domiciliario, estado, fechaCreacion, items, total)
├── repository                       // Interfaces Spring Data JPA
│   ├── AdicionalRepository.java
│   ├── AdministradorRepository.java
│   ├── CategoriaRepository.java
│   ├── ClienteRepository.java
│   ├── ComidaRepository.java
│   ├── DomiciliarioRepository.java
│   ├── ItemPedidoRepository.java
│   ├── OperadorRepository.java
│   └── PedidoRepository.java
├── service                          // Capa de Lógica de Negocio (@Service, @Transactional)
│   ├── AdicionalService.java / AdicionalServiceImpl.java
│   ├── CategoriaService.java / CategoriaServiceImpl.java
│   ├── ClienteService.java / ClienteServiceImpl.java
│   ├── ComidaService.java / ComidaServiceImpl.java
│   ├── DomiciliarioService.java / DomiciliarioServiceImpl.java
│   └── PedidoService.java / PedidoServiceImpl.java
├── controller                       // Controladores MVC (@Controller)
│   ├── HomeController.java          // /, /login, /registro, /admin
│   ├── ComidaController.java        // /comidas (tarjetas, tabla, detalle, crear, guardar)
│   ├── ClienteController.java       // /cliente (portal, actualizar, eliminar)
│   └── PedidoController.java        // /pedidos (listado, detalle, cambiar estado, asignar domiciliario)
└── errors                           // Manejo Global de Errores
    ├── AdicionalNotFoundException.java
    ├── CategoriaNotFoundException.java
    ├── ClienteNotFoundException.java
    ├── ComidaEnUsoException.java
    ├── ComidaNotFoundException.java
    ├── DomiciliarioNotFoundException.java
    ├── PedidoNotFoundException.java
    └── GlobalExceptionHandler.java  // @ControllerAdvice centralizado
```

---

## 🌐 Mapeo de Rutas Principales

- **`GET /`** : Portada principal del Restaurante El Wey.
- **`GET /comidas/tarjetas`** : Menú completo en formato de tarjetas con filtros y buscador.
- **`GET /comidas/tabla`** : Vista de catálogo en formato de tabla.
- **`GET /comidas/{id}`** : Ficha detallada de un platillo mostrando su categoría.
- **`GET /comidas/crear`** : Formulario con `<select>` dinámico de categorías para registrar un plato.
- **`POST /comidas/guardar`** : Guarda un plato asociándolo a la categoría seleccionada.
- **`GET /pedidos`** : Panel de gestión y listado general de pedidos.
- **`GET /pedidos/{id}`** : Ficha de detalle de un pedido (ítems, adicionales, total, cliente y domiciliario).
- **`POST /pedidos/{id}/estado`** : Actualizar estado del pedido (PENDIENTE, EN_PREPARACION, EN_CAMINO, ENTREGADO, CANCELADO).
- **`POST /pedidos/{id}/asignar-domiciliario`** : Asignación de repartidor al pedido.
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
