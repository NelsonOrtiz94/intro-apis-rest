package com.lta.cursoapis.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lta.cursoapis.dto.CategoriaDTO;
import com.lta.cursoapis.mapper.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lta.cursoapis.entity.Categoria;
import com.lta.cursoapis.exceptions.BadRequestException;
import com.lta.cursoapis.exceptions.ResourceNotFoundException;
import com.lta.cursoapis.repository.CategoriaRepository;
import com.lta.cursoapis.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        if(categoriaRepository.existsByNombreCategoria(categoriaDTO.getNombreCategoria())){
            throw new BadRequestException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria nuevaCategoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(nuevaCategoria);
    }

    @Override
    public List<CategoriaDTO> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaDTO> obtenerCategoriaPorId(Long idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        return categoria.map(categoriaMapper::toDTO);
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long idCategoria, CategoriaDTO categoriaDTO) {
        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        categoriaExistente.setNombreCategoria(categoriaDTO.getNombreCategoria());
        Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);
        return categoriaMapper.toDTO(categoriaActualizada);
    }

    @Override
    public void eliminarCategoria(Long idCategoria) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(idCategoria);
        if(!categoriaExistente.isPresent()){
            throw new ResourceNotFoundException("Categoría no encontrada para eliminar.");
        }
        categoriaRepository.deleteById(idCategoria);
    }
}