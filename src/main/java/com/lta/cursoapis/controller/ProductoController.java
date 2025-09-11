package com.lta.cursoapis.controller;

import java.util.List;
import java.util.Optional;

import com.lta.cursoapis.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lta.cursoapis.entity.EstadoProducto;
import com.lta.cursoapis.entity.Producto;
import com.lta.cursoapis.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/registrar/{categoriaId}")
    public ResponseEntity<?> registrarProducto(
            @PathVariable Long categoriaId,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("estado") EstadoProducto estado) {

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombreProducto(nombreProducto);
        productoDTO.setDescripcion(descripcion);
        productoDTO.setPrecio(precio);
        productoDTO.setCantidad(cantidad);
        productoDTO.setEstado(estado);

        ProductoDTO productoBBDD = productoService.registrarProducto(categoriaId,productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoBBDD);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productosDtos = productoService.listarProductos();
        return ResponseEntity.ok(productosDtos);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        Optional<ProductoDTO> productoDTO = productoService.buscarPorNombre(nombre);
        return productoDTO.isPresent() ? ResponseEntity.ok(productoDTO.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idProducto) {
        Optional<ProductoDTO> productoDTO = productoService.buscarPorId(idProducto);
        return productoDTO.isPresent() ? ResponseEntity.ok(productoDTO.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable Long idProducto,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("estado") EstadoProducto estado) {
        try {
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setNombreProducto(nombreProducto);
            productoDTO.setDescripcion(descripcion);
            productoDTO.setPrecio(precio);
            productoDTO.setCantidad(cantidad);
            productoDTO.setEstado(estado);

            ProductoDTO productoActualizado = productoService.actualizarProducto(idProducto, productoDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long idProducto) {
        try {
            productoService.eliminarProducto(idProducto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PutMapping("/estado/{idProducto}")
    public ResponseEntity<?> cambiarEstadoProducto(@PathVariable Long idProducto,
                                                   @RequestBody EstadoProducto estadoProducto) {
        try {
            ProductoDTO productoActualizado = productoService.cambiarEstadoProducto(idProducto, estadoProducto);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProductoDTO>> listarProductosPorEstado(@PathVariable EstadoProducto estado) {
        List<ProductoDTO> productoDTOS = productoService.obtenerProductosPorEstado(estado);
        return ResponseEntity.ok(productoDTOS);
    }
}