package com.lta.cursoapis.service;

import com.lta.cursoapis.entity.EstadoProducto;
import com.lta.cursoapis.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto registrarProducto(Producto producto);

    List<Producto> listarProductos();

    Optional<Producto> findByNombre(String nombre);

    Optional<Producto> findById(Long idProducto);

    Producto actualizarProducto(Long idProducto, Producto producto);

    void eliminarProducto(Long idProducto);

    Producto actualizarEstado(Long idProducto, EstadoProducto estadoProducto);

    List<Producto> listarPorEstado(EstadoProducto estadoProducto);
}
