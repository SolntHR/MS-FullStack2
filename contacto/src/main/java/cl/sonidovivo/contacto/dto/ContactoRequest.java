package cl.sonidovivo.contacto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactoRequest(
        @NotBlank(message = "El nombre es obligatorio.") @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.") String nombre,
        @NotBlank(message = "El correo es obligatorio.") @Email(message = "Debes ingresar un formato de correo válido.") @Size(max = 100, message = "El correo no puede superar los 100 caracteres.") String correo,
        @NotBlank(message = "El comentario es obligatorio.") @Size(max = 500, message = "El comentario no puede superar los 500 caracteres.") String mensaje) {
}
