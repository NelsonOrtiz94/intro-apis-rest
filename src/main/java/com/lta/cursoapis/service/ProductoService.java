package com.lta.cursoapis.service;

import com.lta.cursoapis.dto.ProductoDTO;
import com.lta.cursoapis.entity.EstadoProducto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    ProductoDTO registrarProducto(Long categoriaId, ProductoDTO productoDTO);

    List<ProductoDTO> listarProductos();

    Optional<ProductoDTO> buscarPorNombre(String nombre);

    Optional<ProductoDTO> buscarPorId(Long idProducto);

    ProductoDTO actualizarProducto(Long idProducto,ProductoDTO productoDTO);

    void eliminarProducto(Long idProducto);

    ProductoDTO cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto);

    List<ProductoDTO> obtenerProductosPorEstado(EstadoProducto estadoProducto);
}