package com.lta.cursoapis.repository;

import com.lta.cursoapis.entity.EstadoProducto;
import com.lta.cursoapis.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByIdProducto(Long idProducto);

    Optional<Producto> findByNombreProducto(String nombreProducto);

    // Antes: findByEstadoProducto(...)
    List<Producto> findByEstado(EstadoProducto estado);


}
