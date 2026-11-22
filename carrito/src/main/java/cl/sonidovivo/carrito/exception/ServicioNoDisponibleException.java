package cl.sonidovivo.carrito.exception;

// 503 Service Unavailable: otro microservicio (inventario) no respondió
public class ServicioNoDisponibleException extends RuntimeException {
    public ServicioNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
