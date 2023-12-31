package com.project.tienda.carrito.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    private Long idProducto;
    private String nombre;
    private String marca;
    private Double precio;
    private int cantidadDisponible;
}