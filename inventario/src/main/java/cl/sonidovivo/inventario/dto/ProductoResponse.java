package cl.sonidovivo.inventario.dto;

import cl.sonidovivo.inventario.model.Producto;

public record ProductoResponse(
    Long id,
    String codigo,
    String nombreproducto,
    String categoria,
    Long idCategoria,
    String marca,
    String modelo,
    Integer precio,
    Integer stock,
    String descripcion,
    String imagen
) {
    public static ProductoResponse desde(Producto p) {
        return new ProductoResponse(p.getId(), p.getCodigo(), p.getNombre(), p.getCategoria().getNombre(),
                p.getCategoria().getId(), p.getMarca(), p.getModelo(), p.getPrecio(), p.getStock(),
                p.getDescripcion(), p.getImagen());
    }
}
