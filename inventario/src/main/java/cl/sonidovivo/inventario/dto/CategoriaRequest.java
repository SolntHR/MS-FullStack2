package cl.sonidovivo.inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
    @NotBlank(message = "El nombre de la categoría es obligatorio.") @Size(max = 60) String nombre,
    @Size(max = 250, message = "La descripción no puede superar los 250 caracteres.") String descripcion,
    String imagen
) {
    
}
