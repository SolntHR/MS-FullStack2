package cl.sonidovivo.usuarios.dto;

import cl.sonidovivo.usuarios.validacion.Reglas;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ActualizarPerfilRequest (
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.") String nombre,
        @Pattern(regexp = "^$|" + Reglas.TELEFONO, message = Reglas.TELEFONO_MENSAJE) String telefono,
        @Size(max = 80) String region,
        @Size(max = 80) String comuna,
        String foto
) {
}