package com.project.tienda.venta.controller;

import com.project.tienda.venta.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
public class VentaController {
    @Autowired
    private IVentaService service;
    @PostMapping("/crear")
    public ResponseEntity<?> crearVenta(@RequestParam Long idVenta, @RequestParam Long idCarrito){
       try {
           service.crearVenta(idVenta,idCarrito);
           return new ResponseEntity<>("Venta registrada correctamente", HttpStatus.CREATED);
       } catch (Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
    @GetMapping("/obtenerVenta/{idVenta}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idVenta){
        try {
            return new ResponseEntity<>(service.obtenerPorId(idVenta), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/eliminarVenta/{idVenta}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long idVenta){
        try {
            service.eliminarPorId(idVenta);
            return new ResponseEntity<>("Venta eliminada correctamente", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
