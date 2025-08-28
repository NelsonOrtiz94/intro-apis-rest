package com.lta.cursoapis.controller;

import com.lta.cursoapis.entity.EstadoProducto;
import com.lta.cursoapis.entity.Producto;
import com.lta.cursoapis.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarProducto(@RequestBody Producto producto){
        Producto nuevoProducto = productoService.registrarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre){
        Optional<Producto> producto = productoService.findByNombre(nombre);
        return producto.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con nombre: " + nombre));
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> findById(@PathVariable Long idProducto){
        Optional<Producto> producto = productoService.findById(idProducto);
        return producto.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con id: " + idProducto));
    }

    // Opción simple: actualizar ENTIDAD completa (put id en el body)
    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto, @RequestBody Producto producto) {
        try {
            // Asegura que el servicio actualice el registro correcto
            producto.setIdProducto(idProducto);
            Producto productoBBDD = productoService.actualizarProducto(idProducto, producto);
            return ResponseEntity.ok(productoBBDD);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long idProducto) {
        try{
            productoService.eliminarProducto(idProducto);
            return ResponseEntity.noContent().build();
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    // Cambiar SOLO el estado (via query param)
    public record EstadoRequest(String estadoProducto) {}

    @PutMapping("/estado/{idProducto}")
    public ResponseEntity<?> cambiarEstadoProducto(@PathVariable Long idProducto,
                                                   @RequestBody EstadoRequest request) {
        try {
            Producto productoActualizado =
                    productoService.actualizarEstado(idProducto, EstadoProducto.valueOf(request.estadoProducto().toUpperCase()));
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }


    @GetMapping("/listar/{estado}")
    public ResponseEntity<?> listarProductoPorEstado(@PathVariable String estado) {
        try {
            EstadoProducto filtro = EstadoProducto.valueOf(estado.toUpperCase());
            List<Producto> productos = productoService.listarPorEstado(filtro);
            return ResponseEntity.ok(productos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido: " + estado);
        }
    }
}
