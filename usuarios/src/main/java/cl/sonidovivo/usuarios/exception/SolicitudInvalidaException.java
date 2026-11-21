package cl.sonidovivo.usuarios.exception;

/** Se traduce a 400 Bad Request para reglas de negocio */
public class SolicitudInvalidaException extends RuntimeException {
    public SolicitudInvalidaException(String mensaje) {
        super(mensaje);
    }
}