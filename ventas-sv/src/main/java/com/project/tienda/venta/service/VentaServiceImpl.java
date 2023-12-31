package com.project.tienda.venta.service;

import com.project.tienda.venta.dto.ProductoDTO;
import com.project.tienda.venta.model.Carrito;
import com.project.tienda.venta.model.Producto;
import com.project.tienda.venta.model.Venta;
import com.project.tienda.venta.repository.ICarritoAPIClient;
import com.project.tienda.venta.repository.IProductoAPIClient;
import com.project.tienda.venta.repository.IVentaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaServiceImpl implements IVentaService{
    @Autowired
    private IVentaRepository repository;
    @Autowired
    private ICarritoAPIClient apiCarrito;
    @Autowired
    private IProductoAPIClient apiProducto;
    @Override
    @CircuitBreaker(name = "carrito-sv", fallbackMethod = "fallbackCrearVenta")
    @Retry(name = "carrito-sv")
    public void crearVenta(Long idVenta, Long idCarrito) {
        Venta buscarVenta = this.obtenerPorId(idVenta);
        if (buscarVenta == null){
            Carrito carrito = apiCarrito.obtenerPorId(idCarrito);
            Carrito carritoAGuardar = new Carrito(idCarrito, carrito.getListaProductos(), carrito.getMontoTotal());
            LocalDate fecha = LocalDate.now();
            Venta venta = new Venta(idVenta, fecha, carritoAGuardar);
            actualizarStock(carrito);
            repository.save(venta);
            apiCarrito.eliminarPorId(idCarrito);
        } else {
            throw new RuntimeException("La venta con el ID ingresado ya existe");
        }

    }
    public Venta  fallbackCrearVenta(){
        return new Venta(null, null,null);
    }
    @CircuitBreaker(name = "productos-sv" , fallbackMethod = "fallbackActualizarStock")
    @Retry(name = "productos-sv")
    private void actualizarStock(Carrito carrito){
        List<ProductoDTO> listaProductos = carrito.getListaProductos();

        for (ProductoDTO prod : listaProductos){
            Producto buscarProducto = apiProducto.obtenerPorId(prod.getIdProducto());
            buscarProducto.setCantidadDisponible(buscarProducto.getCantidadDisponible()-1);
            apiProducto.editarProducto(buscarProducto.getIdProducto(), buscarProducto);
        }
    }
    public String fallbackActualizarStock(){
        return "Ha ocurrido un error al intentar comunicarse con el servicio productos-sv";
    }

    @Override
    public List<Venta> obtenerLista() {
        return repository.findAll();
    }

    @Override
    public Venta obtenerPorId(Long idVenta) {
        return repository.findById(idVenta).orElse(null);
    }


    @Override
    public void eliminarPorId(Long idVenta) {
      repository.deleteById(idVenta);
    }
}
