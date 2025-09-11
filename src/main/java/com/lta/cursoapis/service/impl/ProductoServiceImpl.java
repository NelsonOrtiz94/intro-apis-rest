package com.lta.cursoapis.service.impl;

import com.lta.cursoapis.dto.ProductoDTO;
import com.lta.cursoapis.entity.Categoria;
import com.lta.cursoapis.entity.EstadoProducto;
import com.lta.cursoapis.entity.Producto;
import com.lta.cursoapis.exceptions.BadRequestException;
import com.lta.cursoapis.exceptions.ResourceNotFoundException;
import com.lta.cursoapis.mapper.ProductoMapper;
import com.lta.cursoapis.repository.CategoriaRepository;
import com.lta.cursoapis.repository.ProductoRepository;
import com.lta.cursoapis.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public ProductoDTO registrarProducto(Long categoriaId, ProductoDTO productoDTO) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + categoriaId + " no encontrada"));

        if(productoDTO.getPrecio() == null || productoDTO.getPrecio() <= 0){
            throw new BadRequestException("El precio del producto debe ser mayor que 0");
        }

        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setCategoria(categoria);

        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.toDTO(productoGuardado);
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<ProductoDTO> buscarPorNombre(String nombre) {
        Optional<Producto> producto = productoRepository.findByNombreProducto(nombre);
        return producto.map(productoMapper::toDTO);
    }

    @Override
    public Optional<ProductoDTO> buscarPorId(Long idProducto) {
        Optional<Producto> producto = productoRepository.findByIdProducto(idProducto);
        return producto.map(productoMapper::toDTO);
    }

    @Override
    public ProductoDTO actualizarProducto(Long idProducto, ProductoDTO productoDTO){
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + idProducto + " no encontrado"));

        productoExistente.setNombreProducto(productoDTO.getNombreProducto());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setCantidad(productoDTO.getCantidad());
        productoExistente.setEstadoProducto(productoDTO.getEstado());

        if(productoDTO.getCategoria() != null && productoDTO.getCategoria().getIdCategoria() != null){
            Categoria categoria = categoriaRepository.findById(productoDTO.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            productoExistente.setCategoria(categoria);
        }

        Producto productoActualizado = productoRepository.save(productoExistente);
        return productoMapper.toDTO(productoActualizado);
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + idProducto + " no encontrado"));
        productoRepository.deleteById(idProducto);
    }

    @Override
    public ProductoDTO cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto) {
        Producto productoExistente = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + idProducto + " no encontrado"));
        productoExistente.setEstadoProducto(nuevoEstadoProducto);

        Producto productoActualizado = productoRepository.save(productoExistente);
        return productoMapper.toDTO(productoActualizado);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorEstado(EstadoProducto estadoProducto) {
        List<Producto> productos = productoRepository.findByEstadoProducto(estadoProducto);
        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }
}