package com.project.tienda.carrito.repository;

import com.project.tienda.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

@Repository
public interface ICarritoRepository extends JpaRepository<Carrito, Long> {
}
