package com.elwey.restaurante.controller;

import com.elwey.restaurante.entities.Comida;
import com.elwey.restaurante.service.CategoriaService;
import com.elwey.restaurante.service.ComidaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comidas")
public class ComidaController {

    @Autowired
    private ComidaService service;

    @Autowired
    private CategoriaService categoriaService;

    Logger log = LoggerFactory.getLogger(ComidaController.class);

    /**
     * GET /comidas/tabla → Muestra la vista de comida en formato de tabla.
     * Envía al modelo:
     * • comidas → lista completa de platos con su categoría asociada
     * • esAdmin → bandera para activar el modo de administración
     */
    @GetMapping("/tabla")
    public String mostrarTabla(Model model) {
        model.addAttribute("comidas", service.seeAll());
        model.addAttribute("esAdmin", false);
        return "comidas-tabla";
    }

    /**
     * GET /comidas, GET /comidas/, GET /comidas/tarjetas → Muestra la vista de
     * tarjetas del menú.
     * Envía al modelo:
     * • comidas → lista de platos con su categoría para mostrar tarjetas
     */
    @GetMapping({ "", "/", "/tarjetas" })
    public String mostrarTarjetas(Model model) {
        model.addAttribute("comidas", service.seeAll());
        return "comidas-tarjetas";
    }

    /**
     * GET /comidas/{id} → Muestra el detalle completo de una comida específica.
     * Recibe:
     * • id → identificador del plato a consultar
     * Envía al modelo:
     * • comida → datos completos del plato para renderizar la vista de detalle
     */
    @GetMapping("/{id}")
    public String mostrarDetalle(@PathVariable("id") Long id, Model model) {
        Comida comida = service.searchById(id);
        model.addAttribute("comida", comida);
        return "comida-detalle";
    }

    /**
     * GET /comidas/buscar → Busca comidas por nombre desde la vista pública.
     * Recibe:
     * • q → texto de búsqueda
     * Envía al modelo:
     * • comidas → resultados filtrados por nombre
     * • query → texto ingresado para mantenerlo en el formulario
     */
    @GetMapping("/buscar")
    public String buscar(@RequestParam(name = "q", required = false) String query, Model model) {
        model.addAttribute("comidas", service.buscarPorNombre(query));
        model.addAttribute("query", query);
        return "comidas-tarjetas";
    }

    /**
     * GET /comidas/eliminar/{id} → Elimina una comida y redirige al panel
     * administrativo.
     * Recibe:
     * • id → identificador del plato a eliminar
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        log.info("Petición para eliminar comida con ID: " + id);
        service.deleteById(id);
        return "redirect:/admin/productos";
    }

    /**
     * GET /comidas/crear → Muestra formulario vacío para crear un plato nuevo.
     * Envía al modelo:
     * • comida → objeto Comida vacío para th:object
     * • categorias → lista de Categoria para el select dinámico
     */
    @GetMapping("/crear")
    public String mostrarFormCrear(Model model) {
        model.addAttribute("comida", new Comida());
        model.addAttribute("categorias", categoriaService.seeAll());
        model.addAttribute("isEdicion", false);
        return "crear-plato";
    }

    /**
     * POST /comidas/guardar → Guarda el plato recibiendo el objeto y el id de la
     * categoría por @RequestParam.
     */
    @PostMapping("/guardar")
    public String guardarComida(
            @ModelAttribute("comida") Comida comida,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId) {

        log.info("Guardando plato: {} con categoria ID: {}", comida.getNombre(), categoriaId);
        service.save(comida, categoriaId);
        return "redirect:/admin/productos";
    }

    /**
     * GET /comidas/editar/{id} → Muestra formulario pre-relleno con datos del
     * plato.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormEditar(@PathVariable("id") Long id, Model model) {
        Comida comida = service.searchById(id);

        model.addAttribute("comida", comida);
        model.addAttribute("categorias", categoriaService.seeAll());
        model.addAttribute("categoriaActualId", comida.getCategoria() != null ? comida.getCategoria().getId() : null);
        model.addAttribute("isEdicion", true);
        return "crear-plato";
    }

}