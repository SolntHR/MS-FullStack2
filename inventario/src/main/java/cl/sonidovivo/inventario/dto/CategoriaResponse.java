package cl.sonidovivo.inventario.dto;

import cl.sonidovivo.inventario.model.Categoria;

public record CategoriaResponse(
    Long id,
    String nombre,
    String descripcion,
    String imagen,
    long cantidadProductos
) {
    public static CategoriaResponse desde(Categoria c, long cantidadProductos) {
        return new CategoriaResponse(c.getId(), c.getNombre(), c.getDescripcion(), c.getImagen(), cantidadProductos);
    }
}
