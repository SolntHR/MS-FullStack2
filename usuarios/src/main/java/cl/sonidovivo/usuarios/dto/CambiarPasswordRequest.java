package cl.sonidovivo.usuarios.dto;

import cl.sonidovivo.usuarios.validacion.Reglas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CambiarPasswordRequest (
        @NotBlank(message = "Debe ingresar la contraseña actual.") String actual,
        @NotBlank(message = "Debe ingresar la nueva contraseña.") @Pattern(regexp = Reglas.PASSWORD, message = Reglas.PASSWORD_MENSAJE) String nueva
) {
}
