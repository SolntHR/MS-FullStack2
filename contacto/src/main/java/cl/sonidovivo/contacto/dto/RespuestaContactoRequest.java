package cl.sonidovivo.contacto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Texto con que el administrador responde un mensaje */
public record RespuestaContactoRequest(
        @NotBlank(message = "La respuesta es obligatoria.")
        @Size(max = 1000, message = "La respuesta no puede superar los 1000 caracteres.") String respuesta) {
}
