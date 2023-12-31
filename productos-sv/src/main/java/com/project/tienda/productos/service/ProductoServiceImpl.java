package com.project.tienda.productos.service;

import com.project.tienda.productos.model.Producto;
import com.project.tienda.productos.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService{
    @Autowired
    private IProductoRepository repository;
    @Override
    public void crearProducto(Producto producto) {
        repository.save(producto);
    }

    @Override
    public List<Producto> obtenerLista() {
        return repository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminarPorId(Long id) {
     repository.deleteById(id);
    }

    @Override
    public void editarProducto(Producto producto) {
    repository.save(producto);
    }
}
