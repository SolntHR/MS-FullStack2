package cl.sonidovivo.carrito.service;

import cl.sonidovivo.carrito.cliente.InventarioCliente;
import cl.sonidovivo.carrito.cliente.ProductoInventario;
import cl.sonidovivo.carrito.dto.AgregarItemRequest;
import cl.sonidovivo.carrito.dto.CarritoResponse;
import cl.sonidovivo.carrito.exception.ConflictoException;
import cl.sonidovivo.carrito.exception.RecursoNoEncontradoException;
import cl.sonidovivo.carrito.model.ItemCarrito;
import cl.sonidovivo.carrito.repository.ItemCarritoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private ItemCarritoRepository repository;

    @Mock
    private InventarioCliente inventario;

    @InjectMocks
    private CarritoService service;

    private static final ProductoInventario GUITARRA =
            new ProductoInventario(1L, "GA001", "Guitarra Acústica Folk", "Yamaha", "F310", 129990, 3, "/img/f310.png");

    private static ItemCarrito item(int cantidad) {
        ItemCarrito i = new ItemCarrito();
        i.setId(10L);
        i.setIdUsuario(7L);
        i.setIdProducto(1L);
        i.setCodigo("GA001");
        i.setNombre("Guitarra Acústica Folk");
        i.setPrecio(129990);
        i.setCantidad(cantidad);
        return i;
    }

    @Test
    @DisplayName("Agregar un producto nuevo lo guarda con los datos de inventario")
    void agregarNuevo() {
        when(inventario.obtenerPorCodigo("GA001")).thenReturn(GUITARRA);
        when(repository.findByIdUsuarioAndCodigoIgnoreCase(7L, "GA001")).thenReturn(Optional.empty());
        when(repository.findByIdUsuarioOrderByIdAsc(7L)).thenReturn(List.of(item(2)));

        CarritoResponse r = service.agregar(7L, new AgregarItemRequest("GA001", 2));

        verify(repository).save(argThat(i -> i.getCantidad() == 2 && i.getPrecio() == 129990 && i.getIdUsuario() == 7L));
        assertEquals(2, r.cantidadTotal());
        assertEquals(259980, r.total());
    }

    @Test
    @DisplayName("Agregar un producto que ya está suma la cantidad")
    void agregarExistenteSuma() {
        ItemCarrito existente = item(1);
        when(inventario.obtenerPorCodigo("GA001")).thenReturn(GUITARRA);
        when(repository.findByIdUsuarioAndCodigoIgnoreCase(7L, "GA001")).thenReturn(Optional.of(existente));

        service.agregar(7L, new AgregarItemRequest("GA001", 2));

        assertEquals(3, existente.getCantidad());
    }

    @Test
    @DisplayName("No se puede agregar más que el stock disponible")
    void sinStockSuficiente() {
        when(inventario.obtenerPorCodigo("GA001")).thenReturn(GUITARRA);
        when(repository.findByIdUsuarioAndCodigoIgnoreCase(7L, "GA001")).thenReturn(Optional.of(item(2)));

        assertThrows(ConflictoException.class, () -> service.agregar(7L, new AgregarItemRequest("GA001", 2)));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Un producto agotado no se puede agregar")
    void agotado() {
        when(inventario.obtenerPorCodigo("BJ010"))
                .thenReturn(new ProductoInventario(52L, "BJ010", "Bajo de prueba", "Ibanez", "SR300", 279990, 0, null));
        assertThrows(ConflictoException.class, () -> service.agregar(7L, new AgregarItemRequest("BJ010", 1)));
    }

    @Test
    @DisplayName("Actualizar la cantidad de un producto que no está en el carrito da 404")
    void actualizarInexistente() {
        when(repository.findByIdUsuarioAndCodigoIgnoreCase(7L, "XX999")).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> service.actualizarCantidad(7L, "XX999", 1));
    }

    @Test
    @DisplayName("Vaciar borra solo el carrito del usuario")
    void vaciar() {
        service.vaciar(7L);
        verify(repository).deleteByIdUsuario(7L);
    }
}
