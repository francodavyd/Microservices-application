package com.project.tienda.carrito.service;

import com.project.tienda.carrito.dto.ProductoDTO;
import com.project.tienda.carrito.model.Carrito;
import com.project.tienda.carrito.model.Producto;
import com.project.tienda.carrito.repository.ICarritoRepository;
import com.project.tienda.carrito.repository.IProductoAPIClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoServiceImpl implements ICarritoService {
    @Autowired
    private ICarritoRepository repository;
    @Autowired
    private IProductoAPIClient api;

    @Override
    public void crearCarrito(Carrito carrito) {
        repository.save(carrito);
    }

    @Override
    @CircuitBreaker(name = "productos-sv" , fallbackMethod = "fallbackAgregar")
    @Retry(name = "productos-sv")
    public void agregarAlCarrito(Long idCarrito, Long idProducto) {
        //Obtengo el producto consumiendo al servicio productos-sv
        Producto productoRecibido = api.obtenerPorId(idProducto);
        if (productoRecibido.getCantidadDisponible()<= 0){
            throw new RuntimeException("El producto no se encuentra disponible");
        } else {
            //Seteo los datos recibidos
            ProductoDTO productoCarrito = new ProductoDTO(productoRecibido.getIdProducto(), productoRecibido.getNombre(), productoRecibido.getPrecio());
            //Variable para calcular el monto total de una lista de productos
            Double montoTotal = 0.0;


            //Obtengo el carrito para comprobar si existe o no
            Carrito buscarCarrito = this.obtenerPorId(idCarrito);
            //Si no existe se creara un carrito nuevo y se agregara el producto
            if (buscarCarrito == null){
                List<ProductoDTO> listaNueva = new ArrayList<>();
                listaNueva.add(productoCarrito);
                montoTotal = actualizarMonto(listaNueva);
                Carrito crearCarrito = new Carrito(idCarrito, listaNueva, montoTotal);

                this.crearCarrito(crearCarrito);
            }
            //Si el carrito ya existe, obtengo su lista y le agrego el nuevo producto
            else{
                List<ProductoDTO> listaDeProductos = buscarCarrito.getListaProductos();
                listaDeProductos.add(productoCarrito);
                montoTotal = actualizarMonto(listaDeProductos);
                buscarCarrito.setListaProductos(listaDeProductos);
                buscarCarrito.setMontoTotal(montoTotal);

                this.editarCarrito(buscarCarrito);
            }
        }

    }
    public Carrito fallbackAgregar(){
        return new Carrito(null, null, null);
    }

    @Override
    public void quitarDelCarrito(Long idCarrito, Long idProducto, String opcion) {
        //Primero busco el carrito
        Carrito carritoBuscado = this.obtenerPorId(idCarrito);
        //Obtengo la lista de productos de ese carrito
        List<ProductoDTO> productosDelCarrito = carritoBuscado.getListaProductos();
        //Busco y obtengo el producto a eliminar
        ProductoDTO productoObtenido = this.obtenerProductoDeUnaLista(productosDelCarrito, idProducto);
        //Variable para actualizar el monto total luego de borrar productos
        Double montoTotal = 0.0;

        if (productoObtenido != null){
            //Si es "uno" borrara solo una unidad de ese producto
            if (opcion.equalsIgnoreCase("one")){
                productosDelCarrito.remove(productoObtenido);
                montoTotal = actualizarMonto(productosDelCarrito);

            }
            //Si es "todos" borrara todos los productos con el id idProducto
            else if (opcion.equalsIgnoreCase("all")){
              productosDelCarrito.removeIf(productoDTO -> productoDTO.getIdProducto().equals(idProducto));
                montoTotal = actualizarMonto(productosDelCarrito);
            }
            carritoBuscado.setListaProductos(productosDelCarrito);
            carritoBuscado.setMontoTotal(montoTotal);
            this.editarCarrito(carritoBuscado);
        }
    }
    private Double actualizarMonto(List<ProductoDTO> lista){
        Double montoTotal= 0.0;
        for (ProductoDTO prod : lista){
            montoTotal += prod.getPrecio();
        }
        return montoTotal;
    }

    @Override
    public ProductoDTO obtenerProductoDeUnaLista(List<ProductoDTO> lista, Long idProducto) {
            for (ProductoDTO prod : lista){
                if (prod.getIdProducto().equals(idProducto)){
                    return prod;
                }
            }
            return null;
        }


    @Override
    public List<Carrito> obtenerLista() {
        return repository.findAll();
    }

    @Override
    public Carrito obtenerPorId(Long idCarrito) {
        return repository.findById(idCarrito).orElse(null);
    }

    @Override
    public void eliminarPorId(Long idCarrito) {
        repository.deleteById(idCarrito);
    }
    @Override
    public void editarCarrito(@NotNull Carrito carrito) {
        Carrito car = this.obtenerPorId(carrito.getIdCarrito());

        car.setListaProductos(carrito.getListaProductos());
        car.setMontoTotal(carrito.getMontoTotal());
        repository.save(car);
    }
}

