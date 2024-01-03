# Aplicación Spring Boot con arquitectura de microservicios
Este proyecto esta formado por los siguientes servicios:

# Servicio Productos
Es el encargado de las operaciones CRUD de los productos.
--------------------------------------------------------------------------------------------------------
POST localhost:8080/productos-sv/productos/crear

{
"idProducto" : 1,
"nombre" : "string",
"marca" : "string",
"precio": 2000,
"cantidadDisponible" : 10
}

GET localhost:8080/productos-sv/productos/obtenerLista

GET localhost:8080/productos-sv/productos/obtenerProducto/{idProducto}

DELETE localhost:8080/productos-sv/productos/eliminarProducto/{idProducto}

PUT localhost:8080/productos-sv/productos/editarProducto/{idProducto}
--------------------------------------------------------------------------------------------------------

# Servicio Carrito
Este servicio se encarga de agregar o quitar productos de un carrito, se comunica
con el servicio de productos mediante FEIGN, al agregar o quitar un producto se 
almacenara el monto total.
--------------------------------------------------------------------------------------------------------
POST localhost:8080/carrito-sv/carrito/agregarProducto

{
"idCarrito" : 1,
"idProducto" : 1
}

GET localhost:8080/carrito-sv/carrito/obtenerLista

GET localhost:8080/carrito-sv/carrito/obtenerCarrito/{idCarrito}

DELETE localhost:8080/carrito-sv/carrito/eliminarCarrito/{idCarrito}

DELETE localhost:8080/carrito-sv/carrito/quitarProducto/{opcion}

****El parametro opcion es un String, debe ser "one" o "all". Si se selecciona one
solo se eliminara el primer producto encontrado con esa id, y si se selecciona all
se eliminaran todos los productos con esa id****

{
"idCarrito" : 1,
"idProducto" : 1
}
--------------------------------------------------------------------------------------------------------

# Servicio Ventas
Se encarga de registrar las ventas, recibe un carrito desde el servicio carrito mediante FEIGN.
Registrara su lista de productos, el monto total y la fecha de venta. Al realizar una venta, se
descontara la cantidad(stock) de ese producto en el servicio productos y ademas se eliminara el
carrito de su base de datos, quedando su registro solamente en la venta.
--------------------------------------------------------------------------------------------------------
POST localhost:8080/ventas-sv/ventas/crear?idVenta=1&idCarrito=1

GET localhost:8080/ventas-sv/ventas/obtenerLista

GET localhost:8080/ventas-sv/ventas/obtenerVenta/{idVenta}

DELETE localhost:8080/ventas-sv/ventas/eliminarVenta/{idVenta}
--------------------------------------------------------------------------------------------------------

# Patrones de diseño aplicados
* Service registry y service Discovery con Eureka server.
* Balanceo de cargas con Spring cloud Load Balancing.
* Circuit Breaker con Resilience4j.
* Servidor de configuración central.
* Gateway (port: 8080)

# Instalación
Para probar este proyecto, crea las siguientes bases de datos mysql: "productos_bd", "carrito_bd" y "ventas_bd". Luego 
clona este repositorio y ejecuta todos los microservicios. Una vez realizado lo anterior ya esta todo listo para hacer
pruebas mediante POSTMAN o una herramienta similar



