package com.project.tienda.venta.model;

import com.project.tienda.venta.dto.ProductoDTO;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Carrito {
    private Long idCarrito;
    @ElementCollection
    @CollectionTable(name = "lista_productos")
    private List<ProductoDTO> listaProductos;
    private Double montoTotal;
}
