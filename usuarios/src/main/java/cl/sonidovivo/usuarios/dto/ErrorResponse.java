package cl.sonidovivo.usuarios.dto;

import java.time.LocalDateTime;
import java.util.Map;


public record ErrorResponse(
        int status,
        String mensaje,
        Map<String, String> errores,
        LocalDateTime fecha
) {
    public static ErrorResponse de(int status, String mensaje) {
        return new ErrorResponse(status, mensaje, Map.of(), LocalDateTime.now());
    }
}