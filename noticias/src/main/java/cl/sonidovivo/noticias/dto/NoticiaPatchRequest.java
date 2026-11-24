package cl.sonidovivo.noticias.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Datos para editar una noticia con PATCH: todos los campos son opcionales y solo se
 * actualizan los que vienen en la petición. Si un campo de texto viene, no puede quedar vacío.
 * La imagen admite "" para quitarla.
 */
public record NoticiaPatchRequest(
        @Pattern(regexp = ".*\\S.*", message = "La categoría no puede quedar vacía.") @Size(max = 80) String categoria,
        @Pattern(regexp = ".*\\S.*", message = "El título no puede quedar vacío.") @Size(max = 180) String titulo,
        LocalDate fecha,
        @Pattern(regexp = ".*\\S.*", message = "El resumen no puede quedar vacío.") @Size(max = 500) String resumen,
        @Pattern(regexp = "(?s).*\\S.*", message = "El contenido no puede quedar vacío.") String contenido,
        @Size(max = 500) String imagen) {
}
