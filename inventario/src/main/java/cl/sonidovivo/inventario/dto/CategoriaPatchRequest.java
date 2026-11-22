package cl.sonidovivo.inventario.dto;

import jakarta.validation.constraints.Size;

public record CategoriaPatchRequest(
    @Size(min = 1, max = 60, message = "El nombre no puede quedar vacío.") String nombre,
    @Size(max = 250, message = "La descripción no puede superar los 250 caracteres.") String descripcion,
    String imagen
) {
    
}
