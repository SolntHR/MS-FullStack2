package cl.sonidovivo.noticias.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NoticiaRequest(
        @NotBlank(message = "La categoría es obligatoria.") @Size(max = 80) String categoria,
        @NotBlank(message = "El título es obligatorio.") @Size(max = 180) String titulo,
        @NotNull(message = "La fecha es obligatoria.") LocalDate fecha,
        @NotBlank(message = "El resumen es obligatorio.") @Size(max = 500) String resumen,
        @NotBlank(message = "El contenido es obligatorio.") String contenido,
        @Size(max = 500) String imagen) {
}
