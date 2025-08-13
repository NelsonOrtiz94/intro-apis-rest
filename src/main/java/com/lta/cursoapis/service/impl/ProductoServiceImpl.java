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
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new Exception("Producto no encontrado con ID: " + idProducto));

        // Actualizar los campos del producto existente con los nuevos valores
        productoExistente.setDescripcionProducto(producto.getDescripcionProducto());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setCantidad(producto.getCantidad());
        productoExistente.setEstadoProducto(producto.getEstadoProducto());

        return productoRepository.save(productoExistente);
    }

    @Override
    public void eliminarProducto(Long idProducto) {

    }

    @Override
    public Producto actualizarEstado(Long idProducto, EstadoProducto estadoProducto) {
        return null;
    }

    @Override
    public List<Producto> listarPorEstado(EstadoProducto estadoProducto) {
        return List.of();
    }
}
