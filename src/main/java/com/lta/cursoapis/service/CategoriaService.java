package com.lta.cursoapis.service;

import com.lta.cursoapis.dto.CategoriaDTO;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) throws BadRequestException;

    List<CategoriaDTO> listarCategorias();

    Optional<CategoriaDTO> obtenerCategoriaPorId(Long idCategoria);

    CategoriaDTO actualizarCategoria(Long idCategoria, CategoriaDTO categoriaDTO);

    void eliminarCategoria(Long idCategoria);

}