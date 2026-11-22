package cl.sonidovivo.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductoRequest(
    @NotBlank(message = "El código es obligatorio.")
    @Pattern(regexp = "^[A-Za-z0-9-]{3,10}$", message = "El código debe tener entre 3 y 10 letras, números o guiones.")
    String codigo,
    @NotBlank(message = "El nombre del producto es obligatorio.") @Size(max = 120) String nombreproducto,
    Long idCategoria,
    String categoria,
    @NotBlank(message = "La marca es obligatoria.") @Size(max = 60) String marca,
    @NotBlank(message = "El modelo es obligatorio.") @Size(max = 60) String modelo,
    @NotNull(message = "El precio es obligatorio.") @Min(value = 1, message = "El precio mínimo debe ser 1.") Integer precio,
    @NotNull(message = "El stock es obligatorio.") @Min(value = 1, message = "El stock debe ser mínimo 1.") Integer stock,
    @Size(max = 250, message = "La descripción no puede superar los 250 caracteres.") String descripcion,
    String imagen
) {
    
}
