package cl.sonidovivo.carrito.dto;

import cl.sonidovivo.carrito.model.ItemCarrito;

public record ItemCarritoResponse(
        String codigo,
        Long idProducto,
        String nombreproducto,
        String marca,
        String modelo,
        Integer precio,
        String imagen,
        Integer cantidad,
        Integer subtotal
) {
    public static ItemCarritoResponse desde(ItemCarrito i) {
        return new ItemCarritoResponse(i.getCodigo(), i.getIdProducto(), i.getNombre(), i.getMarca(), i.getModelo(),
                i.getPrecio(), i.getImagen(), i.getCantidad(), i.getPrecio() * i.getCantidad());
    }
}
