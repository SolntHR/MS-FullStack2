package cl.sonidovivo.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductoPatchRequest(

    @Pattern (regexp = "^[A-Za-z0-9-]{3,10}$", message = "El código debe tener entre 3 y 10 letras, números o guiones.")
    String codigo,
    @Size(min = 1, max = 120, message = "El nombre del producto no puede quedar vacío.") String nombreproducto,
    Long idCategoria,
    String categoria,
    @Size(min = 1, max = 60, message = "La marca no puede quedar vacía.") String marca,
    @Size(min = 1, max = 60, message = "El modelo no puede quedar vacío.") String modelo,
    @Min(value = 1, message = "El precio mínimo debe ser 1.") Integer precio,
    @Min(value = 0, message = "El stock no puede ser negativo.") Integer stock,
    @Size(max = 250, message = "La descripción no puede superar los 250 caracteres.") String descripcion,
    String imagen
) {
    
}
