package cl.sonidovivo.carrito.dto;

import java.util.List;

public record CarritoResponse(
        List<ItemCarritoResponse> items,
        int cantidadTotal,
        int total
) {
    public static CarritoResponse desde(List<ItemCarritoResponse> items) {
        int cantidad = items.stream().mapToInt(ItemCarritoResponse::cantidad).sum();
        int total = items.stream().mapToInt(ItemCarritoResponse::subtotal).sum();
        return new CarritoResponse(items, cantidad, total);
    }
}
