package cl.sonidovivo.noticias.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.sonidovivo.noticias.dto.ErrorResponse;

@RestControllerAdvice
public class ManejadorErrores {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(400,
                "Hay datos inválidos en la solicitud.", errores, LocalDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> jsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.de(400,
                "El cuerpo de la solicitud no es un JSON válido o tiene valores no permitidos."));
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> noEncontrado(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.de(404, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> general(Exception ex) {
        if (ex instanceof org.springframework.web.ErrorResponse errorSpring) {
            int status = errorSpring.getStatusCode().value();
            String detalle = errorSpring.getBody().getDetail();
            return ResponseEntity.status(status).body(ErrorResponse.de(status,
                    detalle != null ? detalle : ex.getMessage()));
        }
        return ResponseEntity.internalServerError().body(ErrorResponse.de(500,
                "Ocurrió un error interno en el servidor."));
    }
}
