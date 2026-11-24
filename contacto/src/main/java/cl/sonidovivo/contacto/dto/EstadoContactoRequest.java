package cl.sonidovivo.contacto.dto;

import cl.sonidovivo.contacto.model.EstadoContacto;
import jakarta.validation.constraints.NotNull;

public record EstadoContactoRequest(
        @NotNull(message = "El estado es obligatorio.") EstadoContacto estado) {
}
