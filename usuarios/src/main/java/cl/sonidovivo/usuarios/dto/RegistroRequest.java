package cl.sonidovivo.usuarios.dto;

import cl.sonidovivo.usuarios.validacion.Reglas;
import cl.sonidovivo.usuarios.validacion.RutValido;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank (message = "El RUT es obligatorio.") @RutValido String rut,
        @NotBlank(message = "El nombre es obligatorio.") @Size (min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.") String nombre,
        @NotBlank(message = "El correo es obligatorio.") @Email (message = "El correo no tiene un formato válido.") @Size(max = 100) String correo,
        @NotBlank(message = "La contraseña es obligatoria.") @Pattern(regexp = Reglas.PASSWORD, message = Reglas.PASSWORD_MENSAJE) String password,
        @Size(max = 80) String region,
        @Size(max = 80) String comuna,
        @Pattern(regexp = Reglas.TELEFONO, message = Reglas.TELEFONO_MENSAJE) String telefono
) {
}
