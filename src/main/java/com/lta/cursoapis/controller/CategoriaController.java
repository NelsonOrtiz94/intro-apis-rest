package com.lta.cursoapis.controller;

import com.lta.cursoapis.entity.Categoria;
import com.lta.cursoapis.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    public ResponseEntity<Categoria> crearCategorias(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Long idCategoria) throws Exception {
        Optional<Categoria> categoriaOptional = categoriaService.obtenerCategoriaPorId(idCategoria);
        if(!categoriaOptional.isPresent()) {
            return new ResponseEntity<>(categoriaOptional.get(), HttpStatus.NOT_FOUND);
        }else  {
            throw new Exception("Categoría no encontrada");
        }
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long idCategoria, @RequestBody Categoria categoria) {
        try {
            Categoria categoriaActualizada = categoriaService.actualizarCategoria(idCategoria, categoria);
            if(categoriaActualizada != null) {
                return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
            } else {
                throw new Exception("No se encuentra categoria para actualizar");
            }
        }catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long idCategoria) {
        try {
            categoriaService.eliminarCategoria(idCategoria);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
