package com.project.tienda.carrito.controller;

import com.project.tienda.carrito.dto.CarritoProductoDTO;
import com.project.tienda.carrito.model.Carrito;
import com.project.tienda.carrito.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private ICarritoService service;
    @PostMapping("/agregarProducto")
    public ResponseEntity<?> agregarAlCarrito(@RequestBody CarritoProductoDTO dto){
        try {
            String mensaje = comprobarCarrito(dto.getIdCarrito());
            service.agregarAlCarrito(dto.getIdCarrito(), dto.getIdProducto());
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private String comprobarCarrito(Long idCarrito){
        Carrito carrito = service.obtenerPorId(idCarrito);
        if (carrito == null){
            return "Carrito "+idCarrito+ " creado, producto agregado correctamente";
        } else {
            return "Producto agregado correctamente al carrito " +idCarrito;
        }
    }

    @DeleteMapping("/quitarProducto/{opcion}")
    public ResponseEntity<?> quitarDelCarrito(@RequestBody CarritoProductoDTO dto, @PathVariable String opcion){
        try {
            String mensaje= "";
            if (opcion.equalsIgnoreCase("one")){
                mensaje= "Producto eliminado correctamente del carrito " + dto.getIdCarrito();
            } else if (opcion.equalsIgnoreCase("all")){
                mensaje= "Productos eliminados correctamente del carrito " + dto.getIdCarrito();
            }
            service.quitarDelCarrito(dto.getIdCarrito(), dto.getIdProducto(), opcion);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/obtenerLista")
    public ResponseEntity<?> obtenerLista(){
        try {
            return new ResponseEntity<>(service.obtenerLista(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
@GetMapping("/obtenerCarrito/{idCarrito}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idCarrito){
        try {
            return new ResponseEntity<>(service.obtenerPorId(idCarrito), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
}
@DeleteMapping("/eliminarCarrito/{idCarrito}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long idCarrito){
        try {
            service.eliminarPorId(idCarrito);
            return new ResponseEntity<>("Carrito eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
}



}
