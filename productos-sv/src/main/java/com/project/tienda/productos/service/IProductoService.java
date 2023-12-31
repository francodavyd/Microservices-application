package com.project.tienda.productos.service;

import com.project.tienda.productos.model.Producto;

import java.util.List;

public interface IProductoService {
    public void crearProducto(Producto producto);
    public List<Producto> obtenerLista();
    public Producto obtenerPorId(Long id);
    public void eliminarPorId(Long id);
    public void editarProducto(Producto producto);
}
