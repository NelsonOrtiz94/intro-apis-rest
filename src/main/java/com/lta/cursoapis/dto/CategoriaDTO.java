package com.lta.cursoapis.dto;

import com.lta.cursoapis.entity.Categoria;
import com.lta.cursoapis.entity.EstadoProducto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    private Long idCategoria;

    @NotBlank(message = "El nombre de la categoría es obligatoria")
    @Size(min = 3, max = 50, message = "El nombre de la categoría debe tener entre 3 y 50 caracteres")
    private String nombreCategoria;

}