package com.project.tienda.productos.controller;

import com.project.tienda.productos.model.Producto;
import com.project.tienda.productos.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {
@Autowired
    private IProductoService service;

@PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto){
    try {
        service.crearProducto(producto);
        return new ResponseEntity<>("Producto creado correctamente", HttpStatus.CREATED);
    } catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
@GetMapping("/obtenerLista")
    public ResponseEntity<?> obtenerLista(){
    try {
        return new ResponseEntity<>(service.obtenerLista(),HttpStatus.OK);
    } catch (Exception e){
        return new ResponseEntity<>("Algo salio mall: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
@GetMapping("/obtenerProducto/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
    try {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    } catch (Exception e){
        return new ResponseEntity<>("Algo salio mall: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
@DeleteMapping("/eliminarProducto/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
    try {
        service.eliminarPorId(id);
        return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.OK);
    } catch (Exception e){
        return new ResponseEntity<>("Algo salio mall: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

@PutMapping("/editarProducto/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Long id, @RequestBody Producto producto){
    try {
        Producto productoBuscado = service.obtenerPorId(id);
        productoBuscado.setNombre(producto.getNombre());
        productoBuscado.setMarca(producto.getMarca());
        productoBuscado.setPrecio(producto.getPrecio());
        productoBuscado.setCantidadDisponible(producto.getCantidadDisponible());
        service.editarProducto(productoBuscado);
        return new ResponseEntity<>( productoBuscado, HttpStatus.OK);
    } catch(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}


}
