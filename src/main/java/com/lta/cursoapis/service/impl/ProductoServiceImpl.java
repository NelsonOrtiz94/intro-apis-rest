package com.lta.cursoapis.service.impl;

import com.lta.cursoapis.entity.EstadoProducto;
import com.lta.cursoapis.entity.Producto;
import com.lta.cursoapis.repository.ProductoRepository;
import com.lta.cursoapis.service.ProductoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto registrarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreProducto(nombre);
    }

    @Override
    public Optional<Producto> findById(Long idProducto) {
        return productoRepository.findByIdProducto(idProducto);
    }

    @Override
    @SneakyThrows
    public Producto actualizarProducto(Long idProducto, Producto producto) {
        Producto existente = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new Exception("Producto no encontrado con ID: " + idProducto));

        // PUT (actualización completa): copia todos los campos relevantes
        existente.setNombreProducto(producto.getNombreProducto());
        existente.setDescripcionProducto(producto.getDescripcionProducto());
        existente.setPrecio(producto.getPrecio());
        existente.setCantidad(producto.getCantidad());
        existente.setEstado(producto.getEstado()); // <-- antes: setEstadoProducto

        return productoRepository.save(existente);
    }

    @Override
    @SneakyThrows
    public void eliminarProducto(Long idProducto) {
        productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new Exception("Producto con ID " + idProducto + " no encontrado"));
        productoRepository.deleteById(idProducto);
    }

    @Override
    public Producto actualizarEstado(Long idProducto, EstadoProducto estado) {
        Producto existente = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
        existente.setEstado(estado); // <-- antes: setEstadoProducto
        return productoRepository.save(existente);
    }

    @Override
    public List<Producto> listarPorEstado(EstadoProducto estado) {
        return productoRepository.findByEstado(estado); // <-- antes: findByEstadoProducto
    }
}
