# Aplicación Spring Boot con arquitectura de microservicios
Este proyecto esta formado por los siguientes servicios:

# ---------Servicio Productos---------
Es el encargado de las operaciones CRUD de los productos, puedes acceder a su
documentación en: localhost:8080/swagger-ui.html

# ---------Servicio Carrito---------
Este servicio se encarga de agregar o quitar productos de un carrito, se comunica
con el servicio de productos mediante FEIGN, al agregar o quitar un producto se 
almacenara el monto total.
Puedes acceder a su documentación en: localhost:8085/swagger-ui.html

# ---------Servicio Ventas---------
Se encarga de registrar las ventas, recibe un carrito desde el servicio carrito mediante FEIGN.
Registrara su lista de productos, el monto total y la fecha de venta. Al realizar una venta, se
descontara la cantidad(stock) de ese producto en el servicio productos y ademas se eliminara el
carrito de su base de datos, quedando su registro solamente en la venta.
Puedes acceder a su documentación en: localhost:8090/swagger-ui.html

#Patrones de diseño aplicados
* Service registry y service Discovery con Eureka server.
* Balanceo de cargas con Spring cloud Load Balancing.
* Circuit Breaker con Resilience4j.
* Servidor de configuración central.
* Gateway

# Instalación
Para probar este proyecto, crea las siguientes bases de datos mysql: "productos_bd", "carrito_bd" y "ventas_bd". Luego 
clona este repositorio y ejecuta todos los microservicios. Una vez realizado lo anterior ya esta todo listo para hacer
pruebas mediante POSTMAN o una herramienta similar
- En este proyecto se incluye El patron gateway, para utilizarlo coloca localhost:443/nombre-servicio/endpoint-servicio


