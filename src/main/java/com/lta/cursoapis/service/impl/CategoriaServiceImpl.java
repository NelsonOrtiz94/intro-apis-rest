package com.lta.cursoapis.service.impl;

import com.lta.cursoapis.entity.Categoria;
import com.lta.cursoapis.repository.CategoriaRepository;
import com.lta.cursoapis.service.CategoriaService;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @SneakyThrows
    public Categoria crearCategoria(Categoria categoria) {
        if(categoriaRepository.existsByNombreCategoria(categoria.getNombreCategoria())) {
            throw new BadRequestException("La categoría ya existe");
        } else {
            return categoriaRepository.save(categoria);
        }
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    @Override
    @SneakyThrows
    public Categoria actualizarCategoria(Long idCategoria, Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    public void eliminarCategoria(Long idCategoria) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(idCategoria);
        if(!categoriaExistente.isPresent()) {
            throw new RuntimeException("Categoría no encontrada");
        } else {
            categoriaRepository.deleteById(idCategoria);
        }
    }
}
