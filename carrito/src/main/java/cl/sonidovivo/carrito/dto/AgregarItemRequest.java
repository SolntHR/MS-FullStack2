package cl.sonidovivo.carrito.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgregarItemRequest(
        @NotBlank(message = "El código del producto es obligatorio.") String codigo,
        @NotNull(message = "La cantidad es obligatoria.")
        @Min(value = 1, message = "La cantidad mínima es 1.")
        @Max(value = 99, message = "La cantidad máxima por producto es 99.")
        Integer cantidad
) {
}
