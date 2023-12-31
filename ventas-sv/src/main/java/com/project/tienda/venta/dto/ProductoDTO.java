package com.project.tienda.venta.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private Double precio;
}
