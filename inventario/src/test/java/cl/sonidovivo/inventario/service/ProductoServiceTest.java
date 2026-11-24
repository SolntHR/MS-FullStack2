package cl.sonidovivo.inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.sonidovivo.inventario.dto.FiltroProductos;
import cl.sonidovivo.inventario.dto.ProductoPatchRequest;
import cl.sonidovivo.inventario.dto.ProductoRequest;
import cl.sonidovivo.inventario.dto.ProductoResponse;
import cl.sonidovivo.inventario.exception.ConflictoException;
import cl.sonidovivo.inventario.exception.SolicitudInvalidaException;
import cl.sonidovivo.inventario.model.Categoria;
import cl.sonidovivo.inventario.model.Producto;
import cl.sonidovivo.inventario.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoService service;

    private static Categoria categoria() {
        Categoria c = new Categoria();
        c.setId(1L);
        c.setNombre("Guitarras Acústicas");
        return c;
    }

    private static ProductoRequest request(String codigo, Long idCategoria) {
        return new ProductoRequest(codigo, "Guitarra Acústica Folk", idCategoria, null, "Yamaha", "F310",
                129990, 8, "Tapa de abeto", null);
    }

    private static Producto producto() {
        Producto p = new Producto();
        p.setId(10L);
        p.setCodigo("GA001");
        p.setNombre("Guitarra Acústica Folk");
        p.setCategoria(categoria());
        p.setMarca("Yamaha");
        p.setModelo("F310");
        p.setPrecio(129990);
        p.setStock(8);
        return p;
    }

    @Test
    @DisplayName("Crear un producto con un código que ya existe lanza Conflicto")
    void crearCodigoDuplicado() {
        when(repository.existsByCodigoIgnoreCase("GA001")).thenReturn(true);

        assertThrows(ConflictoException.class, () -> service.crear(request("ga001", 1L)));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Crear un producto sin categoría lanza SolicitudInvalida")
    void crearSinCategoria() {
        when(repository.existsByCodigoIgnoreCase("GA009")).thenReturn(false);

        assertThrows(SolicitudInvalidaException.class, () -> service.crear(request("GA009", null)));
    }

    @Test
    @DisplayName("Crear guarda el código en mayúsculas y la categoría indicada por id")
    void crearProducto() {
        when(repository.existsByCodigoIgnoreCase("GA009")).thenReturn(false);
        when(categoriaService.buscar(1L)).thenReturn(categoria());
        when(repository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        ProductoResponse r = service.crear(request("ga009", 1L));

        assertEquals("GA009", r.codigo());
        assertEquals("Guitarras Acústicas", r.categoria());
        assertEquals(8, r.stock());
    }

    @Test
    @DisplayName("Filtrar con precioMin mayor que precioMax lanza SolicitudInvalida")
    void filtrarRangoInvalido() {
        FiltroProductos filtro = new FiltroProductos(null, null, null, 500000, 100000, null);

        assertThrows(SolicitudInvalidaException.class, () -> service.filtrar(filtro));
    }

    @Test
    @DisplayName("PATCH con solo el stock cambia el stock y conserva el precio")
    void actualizarSoloStock() {
        Producto p = producto();
        when(repository.findById(10L)).thenReturn(Optional.of(p));
        when(repository.save(p)).thenReturn(p);

        ProductoResponse r = service.actualizar(10L,
                new ProductoPatchRequest(null, null, null, null, null, null, null, 0, null, null));

        assertEquals(0, r.stock());
        assertEquals(129990, r.precio());
    }
}
