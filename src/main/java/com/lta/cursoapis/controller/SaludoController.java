package com.lta.cursoapis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Especificamente para API REST , controlador REST
// @RestController combina @Controller y @ResponseBody
// No vistas HTML, solo respuestas JSON o XML
@RestController
// Ruta base para este controlador
// Todas las rutas de este controlador comenzarán con /prueba
@RequestMapping("/prueba")
public class SaludoController {

    // Método que maneja solicitudes GET a la ruta /prueba
    @GetMapping("/saludo")
    public String saludar() {
        // Retorna un saludo simple
        return "Hola desde el controlador de prueba";
    }
}
