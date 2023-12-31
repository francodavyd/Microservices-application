package com.project.tienda.venta.repository;

import com.project.tienda.venta.model.Carrito;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "carrito-sv", url = "http://localhost:8085/carrito")
public interface ICarritoAPIClient {
    @GetMapping("/obtenerCarrito/{idCarrito}")
    public Carrito obtenerPorId(@PathVariable Long idCarrito);
    @DeleteMapping("/eliminarCarrito/{idCarrito}")
    public void eliminarPorId(@PathVariable Long idCarrito);
}
