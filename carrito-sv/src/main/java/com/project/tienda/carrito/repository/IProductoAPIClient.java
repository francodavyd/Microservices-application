package com.project.tienda.carrito.repository;

import com.project.tienda.carrito.model.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="productos-sv" , url = "http://localhost:8080/productos")
public interface IProductoAPIClient {
    @GetMapping("/obtenerProducto/{idProducto}")
    public Producto obtenerPorId(@PathVariable Long idProducto);
}
