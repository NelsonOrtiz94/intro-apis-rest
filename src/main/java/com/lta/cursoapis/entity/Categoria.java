package com.lta.cursoapis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"padre", "subcategorias"})
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre_categoria", nullable = false, length = 50)
    private String nombreCategoria;

    // Padre de esta categoría (FK distinta a la PK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria_padre") // <-- NUEVA COLUMNA EN LA TABLA
    @ToString.Exclude
    private Categoria padre;

    // Hijas de esta categoría (lado inverso)
    @OneToMany(mappedBy = "padre")
    @ToString.Exclude
    private List<Categoria> subcategorias;
}
