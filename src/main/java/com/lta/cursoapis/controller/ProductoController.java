package com.lta.cursoapis.controller;

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
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre){
        Optional<Producto> producto = productoService.findByNombre(nombre);
        return producto.isPresent() ? ResponseEntity.ok(producto.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con nombre: " + nombre);
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> findById(@PathVariable Long idProducto){
        Optional<Producto> producto = productoService.findById(idProducto);
        return producto.isPresent()
                ? ResponseEntity.ok(producto.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Producto no encontrado con id: " + idProducto);
    }

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto, @RequestBody Producto producto) {
      try{
          Producto productoActualizado = new Producto();
          productoActualizado.setNombreProducto(producto.getNombreProducto());
            productoActualizado.setDescripcionProducto(producto.getDescripcionProducto());
            productoActualizado.setPrecio(producto.getPrecio());
            productoActualizado.setCantidad(producto.getCantidad());
            productoActualizado.setEstadoProducto(producto.getEstadoProducto());
            Producto productoBBDD = productoService.actualizarProducto(idProducto, productoActualizado);
            return ResponseEntity.ok(productoBBDD);

      } catch (Exception exception){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
      }
    }

    public ResponseEntity<?> eliminarProducto(@PathVariable Long idProducto) {
        try{
            productoService.eliminarProducto(idProducto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
