package com.project.tienda.venta.service;

import com.project.tienda.venta.model.Venta;

import java.util.List;

public interface IVentaService {
    public void crearVenta(Long idVenta, Long idCarrito);
    public List<Venta> obtenerLista();
    public Venta obtenerPorId(Long idVenta);
    public void eliminarPorId(Long idVenta);
}
