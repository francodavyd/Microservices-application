package com.project.tienda.carrito.service;

import com.project.tienda.carrito.dto.ProductoDTO;
import com.project.tienda.carrito.model.Carrito;

import java.util.List;

public interface ICarritoService {
    public void crearCarrito(Carrito carrito);
    public void agregarAlCarrito(Long idCarrito, Long idProducto);
    public void quitarDelCarrito(Long idCarrito, Long idProducto, String opcion);
    public ProductoDTO obtenerProductoDeUnaLista(List<ProductoDTO> lista, Long idProducto);
    public List<Carrito> obtenerLista();
    public Carrito obtenerPorId(Long idCarrito);
    public void eliminarPorId(Long idCarrito);
    public void editarCarrito(Carrito carrito);
}
