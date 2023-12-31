package com.project.tienda.venta.repository;

import com.project.tienda.venta.model.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient( name = "productos-sv", url = "http://localhost:8080/productos")
public interface IProductoAPIClient {
    @GetMapping("/obtenerProducto/{idProducto}")
    public Producto obtenerPorId(@PathVariable Long idProducto);
    @PutMapping("/editarProducto/{idProducto}")
    public void editarProducto(@PathVariable Long idProducto, @RequestBody Producto producto);
}
