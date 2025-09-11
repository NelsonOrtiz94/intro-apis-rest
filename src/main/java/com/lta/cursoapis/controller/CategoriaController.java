package com.lta.cursoapis.controller;

import com.lta.cursoapis.dto.CategoriaDTO;
import com.lta.cursoapis.entity.Categoria;
import com.lta.cursoapis.exceptions.ResourceNotFoundException;
import com.lta.cursoapis.service.CategoriaService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
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

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws BadRequestException {
        CategoriaDTO nuevaCategoria = categoriaService.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias(){
        List<CategoriaDTO> categoriasDtos = categoriaService.listarCategorias();
        return new ResponseEntity<>(categoriasDtos,HttpStatus.OK);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable Long idCategoria) throws Exception {
        Optional<CategoriaDTO> categoriaOptional = categoriaService.obtenerCategoriaPorId(idCategoria);
        if(categoriaOptional.isPresent()){
            return new ResponseEntity<>(categoriaOptional.get(),HttpStatus.OK);
        }
        else{
            throw new ResourceNotFoundException("Categoría no encontrada");
        }
    }

    @PutMapping("{idCategoria}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long idCategoria,@RequestBody CategoriaDTO categoriaDTO){
        try{
            CategoriaDTO categoriaActualizada = categoriaService.actualizarCategoria(idCategoria,categoriaDTO);
            if(categoriaActualizada != null){
                return new ResponseEntity<>(categoriaActualizada,HttpStatus.OK);
            }
            else{
                throw new ResourceNotFoundException("Categoría no encontrada para actualizar");
            }
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long idCategoria){
        try{
            categoriaService.eliminarCategoria(idCategoria);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}