package cl.sonidovivo.carrito.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductoInventario(
        Long id,
        String codigo,
        String nombreproducto,
        String marca,
        String modelo,
        Integer precio,
        Integer stock,
        String imagen
) {
}
