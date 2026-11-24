package cl.sonidovivo.noticias.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NoticiaPatchRequest(
        @Pattern(regexp = ".*\\S.*", message = "La categoría no puede quedar vacía.") @Size(max = 80) String categoria,
        @Pattern(regexp = ".*\\S.*", message = "El título no puede quedar vacío.") @Size(max = 180) String titulo,
        LocalDate fecha,
        @Pattern(regexp = ".*\\S.*", message = "El resumen no puede quedar vacío.") @Size(max = 500) String resumen,
        @Pattern(regexp = "(?s).*\\S.*", message = "El contenido no puede quedar vacío.") String contenido,
        @Size(max = 500) String imagen) {
}
