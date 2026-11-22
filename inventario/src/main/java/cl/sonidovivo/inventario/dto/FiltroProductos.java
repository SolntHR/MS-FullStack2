package cl.sonidovivo.inventario.dto;

import java.util.List;

public record FiltroProductos(
    List<String> categoria,
    String texto,
    String marca,
    Integer precioMin,
    Integer precioMax,
    Boolean soloDisponibles
) {
    
}
