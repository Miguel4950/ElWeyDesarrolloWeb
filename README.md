# 🌮 Restaurante El Wey - Aplicación Web Spring Boot & Thymeleaf

> **Materia:** Desarrollo Web  
> **Entrega:** Entrega 1 (Consolidado de los Sprints 1, 2, 3, 4 y 5)  
> **Repositorio Oficial:** [https://github.com/Miguel4950/ElWeyDesarrolloWeb](https://github.com/Miguel4950/ElWeyDesarrolloWeb)  
> **Tecnologías:** Java 17, Spring Boot 3, Spring Data JPA, H2 Database (In-Memory), Thymeleaf, HTML5, Vanilla CSS / Bootstrap / Tailwind.

---

## 📖 Descripción General del Proyecto

**Restaurante "El Wey"** es una solución web integral diseñada para la gestión operativa y comercial de un restaurante temático de alta cocina mexicana tradicional. El sistema abarca desde la experiencia digital del cliente (exploración de menús, catálogo de platos, consulta de ingredientes y selección de adicionales, gestión de perfil) hasta la administración operativa y logística del restaurante (control de inventario, gestión de pedidos en tiempo real, asignación de domiciliarios y seguimiento de estados de despacho).

El proyecto fue desarrollado de forma incremental y modular a través de **5 Sprints** que conforman la **Entrega 1** de la asignatura.

---

## 🌮 Descripción del Negocio

La propuesta de valor de **El Wey** fusiona la gastronomía autóctona mexicana (tacos al pastor, birria, enchiladas, moles artesanales, antojitos y bebidas tradicionales) con una plataforma tecnológica moderna y eficiente:

1. **Clientes / Comensales:**
   - Visualización de la carta en formato interactivo de **tarjetas** (`/comidas/tarjetas`) o en **tabla analítica** (`/comidas/tabla`).
   - Búsqueda y filtrado dinámico de platillos por nombre y categoría.
   - Ficha detallada por platillo (`/comidas/{id}`) donde se especifican ingredientes, acompañamientos, categoría asignada y los **adicionales disponibles según su categoría**.
   - Registro de usuarios (`/registro`), inicio de sesión (`/login`) y administración del perfil del cliente (`/cliente/portal/{id}`).
   - Seguimiento del historial y estado de órdenes de compra.

2. **Operadores y Cocina:**
   - Recepción, consulta y despacho de pedidos activos (`/pedidos`).
   - Transición de estados del pedido: `PENDIENTE` ➔ `EN_PREPARACION` ➔ `EN_CAMINO` ➔ `ENTREGADO` (o `CANCELADO`).
   - Asignación de repartidores/domiciliarios disponibles para la entrega.

3. **Administradores:**
   - Control total del inventario de comidas (creación, edición y descarte de productos).
   - Gestión de categorías y adicionales disponibles.
   - Directorio y administración general de clientes registrados (`/admin/vertodo`).

---

## 🏆 Matriz de Cumplimiento: Sprints 1 al 5 (Entrega 1)

El desarrollo del proyecto siguió el ciclo evolutivo establecido para la materia, consolidando en esta **Entrega 1** la totalidad de los requerimientos desde la concepción hasta la persistencia y operaciones avanzadas:

### 🔹 Sprint 1: Bocetación y Concepción Inicial
- Levantamiento de requisitos y definición del modelo de negocio de "El Wey".
- Diseño de bocetos preliminares (wireframing) y flujos conceptuales de navegación para clientes y administradores.

---

### 🔹 Sprint 2: Mockups, Navegación, Capas MVC y Primer Catálogo Spring Boot
| Requisito del Sprint 2 | Evidencia de Cumplimiento en el Proyecto |
| :--- | :--- |
| **Mockups de 10 páginas asociadas a URLs** | Diseñadas y enlazadas las rutas base: `/`, `/comidas/tarjetas`, `/comidas/tabla`, `/comidas/{id}`, `/comidas/crear`, `/comidas/editar/{id}`, `/login`, `/registro`, `/cliente/portal/{id}`, `/admin/productos`. |
| **Diagrama de navegación entre páginas** | Estructurada la navegación bidireccional entre la portada, el menú, los detalles, el acceso de clientes y el área de administración. |
| **Modelo de capas + MVC con falsa BD** | Implementación del patrón Controlador-Servicio-Modelo con repositorios en memoria iniciales para comidas. |
| **Catálogo en formato de tabla** | Vista Thymeleaf funcional en `/comidas/tabla` con listado tabular y acciones. |
| **Catálogo en formato de tarjetas** | Vista Thymeleaf interactiva en `/comidas/tarjetas` con buscador en tiempo real. |
| **Ficha individual de una comida** | Vista de detalle en `/comidas/{id}` mostrando la información completa del plato. |
| **Manejo de imágenes por URL (String)** | Atributo `imagenUrl` mapeado como cadena de texto apuntando a recursos multimedia. |
| **Migración a Spring Boot** | Todo el trabajo del Sprint 1 consolidado en un proyecto Spring Boot Maven con Thymeleaf. |

---

### 🔹 Sprint 3: CRUDs, Autenticación y Decisiones de Estilo
| Requisito del Sprint 3 | Evidencia de Cumplimiento en el Proyecto |
| :--- | :--- |
| **Sin persistencia en base de datos real** | Gestión mediante colecciones y lógica de servicios en memoria previa a la migración JPA. |
| **Evaluación de frameworks de estilos** | Pruebas e integración de estilos: uso de utilidades inspiradas en Bootstrap, Tailwind y consolidación con **Vanilla CSS moderno** (`style.css`, `pedidos.css`) para máxima flexibilidad visual. |
| **CRUD completo para Comidas** | Métodos crear (`/comidas/crear`), listar (`/comidas/tabla`), consultar (`/comidas/{id}`), actualizar (`/comidas/editar/{id}`) y eliminar implementados. |
| **CRUD completo para Clientes** | Registro de comensales, actualización de datos en el portal privado y eliminación lógica. |
| **Inicio de sesión (Login)** | Formulario funcional de autenticación en `/login` con redirección controlada por rol. |
| **Repositorio de Categorías con datos iniciales** | Asociación de cada comida a su categoría correspondiente (Tacos, Entradas, Fuertes, Bebidas, Postres). |

---

### 🔹 Sprint 4: Spring Data JPA, H2, Restricciones DDL, Errores y DER
| Requisito del Sprint 4 | Evidencia de Cumplimiento en el Proyecto |
| :--- | :--- |
| **Diagrama Entidad-Relación (DER)** | Modelado completo del esquema de base de datos con tipos de datos, PK, FK y restricciones (`Diagramas/DiagramaER.png`). |
| **Persistencia con Spring Data JPA y H2** | Migración completa a `spring-boot-starter-data-jpa` con base de datos en memoria H2 (`jdbc:h2:mem:restaurantdb`). |
| **Restricciones en Entidades JPA** | Notaciones `@Column(nullable = false, unique = true, length = ...)` en campos críticos (`correo`, `cedula`, `nombre`, etc.) y `@Id @GeneratedValue(strategy = IDENTITY)`. |
| **Prevención de bucles infinitos (Lombok)** | Sustitución de `@Data` por anotaciones granulares (`@Getter`, `@Setter`, `@ToString(exclude = ...)`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`) en entidades con relaciones bidireccionales. |
| **Manejo Global de Excepciones** | Componente `@ControllerAdvice` (`GlobalExceptionHandler`) mapeando errores a una vista unificada `error.html`. |
| **Población inicial de datos (DataLoader)** | Precarga mediante `CommandLineRunner` con **40 comidas**, **5 categorías** y **10 clientes**. |
| **Relación Comida ↔ Categoría** | `@ManyToOne` en `Comida` y `@OneToMany(mappedBy = "categoria")` en `Categoria`, visualizando la categoría en el detalle del plato. |

---

### 🔹 Sprint 5: Modelo Relacional Completo, Pedidos, Domiciliarios, Adicionales y Flujos de BD
| Requisito del Sprint 5 | Evidencia de Cumplimiento en el Proyecto |
| :--- | :--- |
| **Todos los Repositorios y Entidades en BD** | Entidades y repositorios creados con sus relaciones completas: `Pedido`, `ItemPedido`, `Domiciliario`, `Adicional`, `Operador`, `Administrador`, `Cliente`, `Comida`, `Categoria`. |
| **Visualización unificada con datos de BD** | Toda la información mostrada en las vistas proviene directamente de la base de datos relacional H2 vía Spring Data JPA. |
| **Carga de al menos 5 objetos por tabla** | `DataLoader.java` carga ampliamente el mínimo requerido en todas las tablas: **5 Categorías**, **40 Comidas**, **10 Clientes**, **6 Domiciliarios**, **7 Adicionales**, **2 Operadores**, **2 Administradores** y múltiples **Pedidos** con sus respectivos **Ítems de Pedido**. |
| **Adicionales disponibles por categoría en Detalle de Comida** | Al ingresar a `/comidas/{id}`, la vista consulta y lista los adicionales disponibles (`Adicional`) acordes a la categoría del plato seleccionado. |
| **Visualización y gestión de Pedidos generados** | Vistas `/pedidos` y `/pedidos/{id}` que exhiben el desglose de productos solicitados, cantidades, subtotales, adicionales elegidos, datos del cliente, total de la orden y domiciliario asignado. |
| **Operaciones de actualización y mantenimiento de relaciones** | Flujo de asignación de domiciliarios (`POST /pedidos/{id}/asignar-domiciliario`) y cambio de estado (`POST /pedidos/{id}/estado`) preservando la integridad referencial. |
| **Manejo de errores y validación en eliminación/actualización** | Excepciones personalizadas controladas: `PedidoNotFoundException`, `DomiciliarioNotFoundException`, `AdicionalNotFoundException`, `ComidaEnUsoException` (protección al intentar eliminar comidas asociadas a pedidos) y `ComidaNotFoundException`. |

---

## 📐 Diagramas del Sistema

Los diagramas arquitectónicos y de datos actualizados se encuentran ubicados en el directorio [`Diagramas/`](Diagramas/):

1. **Diagrama de Clases UML (`Diagramas/DiagramaDeClases.png`):**
   - Modela la jerarquía y relaciones entre `Cliente`, `Pedido`, `ItemPedido`, `Producto (Comida)`, `Adicional`, `Domiciliario`, `Operador` y `Administrador`.
2. **Diagrama Entidad-Relación Físico (`Diagramas/DiagramaER.png`):**
   - Esquema relacional con llaves primarias, llaves foráneas, tipos de datos SQL, cardinalidades (1:N, N:M resueltas) y restricciones de base de datos.
3. **Diagrama Relacional de Entidades (`Diagramas/Untitled (1) (1).png`):**
   - Diagrama exportado con notación de pata de gallo (Crow's Foot) que ilustra con exactitud las tablas maestras, intermedias y sus llaves en la base de datos.

---

## 🏛️ Arquitectura del Proyecto

El código fuente sigue rigurosamente el patrón de arquitectura en capas (Layered Architecture):

```
com.elwey.restaurante
├── RestauranteApplication.java          // Clase principal y punto de entrada Spring Boot
├── DataLoader.java                      // Carga inicial automatizada de datos en H2 (Sprint 4 y 5)
├── entities                             // Entidades JPA con anotaciones de persistencia y restricciones
│   ├── Adicional.java                   // Extras/toppings opcionales vinculados a comidas/ítems
│   ├── Administrador.java               // Credenciales y rol de administración general
│   ├── Categoria.java                   // Clasificación del menú (Tacos, Entradas, Bebidas, etc.)
│   ├── Cliente.java                     // Comensal registrado (nombre, correo, teléfono, dirección, pedidos)
│   ├── Comida.java                      // Platillo de la carta (nombre, precio, descripción, categoría, estado)
│   ├── Domiciliario.java                // Repartidor (cédula, nombre, celular, disponibilidad)
│   ├── ItemPedido.java                  // Detalle de línea de orden (comida, cantidad, subtotal, adicionales)
│   ├── Operador.java                    // Personal de cocina y despacho
│   └── Pedido.java                      // Orden de compra (cliente, domiciliario, estado, fecha, total, ítems)
├── repository                           // Capa de acceso a datos (Spring Data JPA)
│   ├── AdicionalRepository.java
│   ├── AdministradorRepository.java
│   ├── CategoriaRepository.java
│   ├── ClienteRepository.java
│   ├── ComidaRepository.java
│   ├── DomiciliarioRepository.java
│   ├── ItemPedidoRepository.java
│   ├── OperadorRepository.java
│   └── PedidoRepository.java
├── service                              // Capa de lógica de negocio (@Service, @Transactional)
│   ├── AdicionalService.java / AdicionalServiceImpl.java
│   ├── CategoriaService.java / CategoriaServiceImpl.java
│   ├── ClienteService.java / ClienteServiceImpl.java
│   ├── ComidaService.java / ComidaServiceImpl.java
│   ├── DomiciliarioService.java / DomiciliarioServiceImpl.java
│   └── PedidoService.java / PedidoServiceImpl.java
├── controller                           // Capa de presentación y endpoints web (@Controller MVC)
│   ├── HomeController.java              // Rutas raíz (/), login, registro, portal admin
│   ├── ComidaController.java            // Catálogos (/tarjetas, /tabla), detalle (/comidas/{id}), CRUD
│   ├── ClienteController.java           // Portal del cliente y operaciones de perfil
│   └── PedidoController.java            // Panel de pedidos, detalle de orden, cambio de estado y asignación
└── errors                               // Manejo centralizado y desacoplado de errores
    ├── AdicionalNotFoundException.java
    ├── CategoriaNotFoundException.java
    ├── ClienteNotFoundException.java
    ├── ComidaEnUsoException.java
    ├── ComidaNotFoundException.java
    ├── DomiciliarioNotFoundException.java
    ├── PedidoNotFoundException.java
    └── GlobalExceptionHandler.java      // @ControllerAdvice que intercepta excepciones hacia error.html
```

---

## 🌐 Mapeo de Rutas Principales del Sistema

| Módulo | Método | URL | Descripción |
| :--- | :---: | :--- | :--- |
| **Inicio & Auth** | `GET` | `/` | Portada principal del restaurante El Wey. |
| | `GET` | `/login` | Formulario de autenticación de usuarios. |
| | `GET` | `/registro` | Formulario de registro para nuevos clientes. |
| | `GET` | `/admin/vertodo` | Directorio de clientes registrados (vista admin). |
| | `GET` | `/admin/productos` | Gestión y tabla administrativa de comidas. |
| **Comidas / Menú** | `GET` | `/comidas/tarjetas` | Menú interactivo en formato de tarjetas con buscador en vivo. |
| | `GET` | `/comidas/tabla` | Vista de catálogo en formato de tabla con acciones de gestión. |
| | `GET` | `/comidas/{id}` | Ficha de detalle de un plato con sus ingredientes, categoría y adicionales disponibles. |
| | `GET` | `/comidas/crear` | Formulario de registro de un nuevo plato con selector de categoría. |
| | `POST`| `/comidas/guardar` | Persistencia del plato creado o editado. |
| | `GET` | `/comidas/editar/{id}`| Formulario de edición de un platillo existente. |
| | `GET` | `/comidas/eliminar/{id}`| Eliminación lógica o física con validación de no uso en pedidos. |
| **Clientes** | `GET` | `/cliente/portal/{id}` | Portal privado del cliente para ver y actualizar sus datos personales. |
| | `POST`| `/cliente/actualizar` | Guardado de modificaciones del perfil del cliente. |
| **Pedidos & Logística** | `GET` | `/pedidos` | Panel principal de gestión y listado de todos los pedidos generados. |
| | `GET` | `/pedidos/{id}` | Vista detallada del pedido: comensal, platos, adicionales, subtotal, total y repartidor. |
| | `POST`| `/pedidos/{id}/estado` | Actualización del flujo de estado (`PENDIENTE`, `EN_PREPARACION`, `EN_CAMINO`, `ENTREGADO`). |
| | `POST`| `/pedidos/{id}/asignar-domiciliario` | Asignación de un domiciliario disponible al pedido. |
| **Base de Datos** | `GET` | `/h2` | Consola web gráfica de la base de datos H2 en memoria. |

---

## ⚙️ Conexión a la Consola H2

Para verificar las tablas, relaciones y datos precargados en tiempo real:

1. Ejecutar la aplicación e ingresar en el navegador a: `http://localhost:8080/h2`
2. Configurar los siguientes parámetros de conexión:
   - **Saved Settings:** `Generic H2 (Embedded)`
   - **Driver Class:** `org.h2.Driver`
   - **JDBC URL:** `jdbc:h2:mem:restaurantdb`
   - **User Name:** `sa`
   - **Password:** *(dejar vacío)*
3. Hacer clic en **Connect** para explorar las tablas generadas por JPA: `COMIDAS`, `CATEGORIAS`, `CLIENTES`, `PEDIDOS`, `ITEM_PEDIDO`, `DOMICILIARIOS`, `ADICIONALES`, `OPERADORES` y `ADMINISTRADORES`.

---

## 🚀 Instrucciones de Ejecución

### Requisitos Previos:
- **Java Development Kit (JDK):** Versión 17 o superior instalada.
- **Maven:** Opcional (el proyecto incluye Maven Wrapper ejecutable).

### 1. Clonar el Repositorio:
```bash
git clone https://github.com/Miguel4950/ElWeyDesarrolloWeb.git
cd ElWeyDesarrolloWeb
```

### 2. Ejecutar la Aplicación:
- **En Windows (CMD / PowerShell con Maven Wrapper):**
  ```powershell
  .\mvnw.cmd spring-boot:run
  ```
- **En Linux / macOS:**
  ```bash
  ./mvnw spring-boot:run
  ```
- **Con Maven instalado globalmente:**
  ```bash
  mvn spring-boot:run
  ```

### 3. Acceder a la Plataforma:
- Abrir en el navegador: **`http://localhost:8080`**
- Consola de base de datos: **`http://localhost:8080/h2`**
