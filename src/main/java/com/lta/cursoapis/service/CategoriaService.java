package com.lta.cursoapis.service;

import com.lta.cursoapis.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    Categoria crearCategoria(Categoria categoria);

    List<Categoria> listarCategorias();

    Optional<Categoria> obtenerCategoriaPorId(Long idCategoria);

    Categoria actualizarCategoria(Long idCategoria, Categoria categoria);

    void eliminarCategoria(Long idCategoria);
}
