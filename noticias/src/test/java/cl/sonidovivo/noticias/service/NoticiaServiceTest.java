package cl.sonidovivo.noticias.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.sonidovivo.noticias.dto.NoticiaPatchRequest;
import cl.sonidovivo.noticias.dto.NoticiaRequest;
import cl.sonidovivo.noticias.dto.NoticiaResponse;
import cl.sonidovivo.noticias.exception.RecursoNoEncontradoException;
import cl.sonidovivo.noticias.model.Noticia;
import cl.sonidovivo.noticias.repository.NoticiaRepository;

@ExtendWith(MockitoExtension.class)
class NoticiaServiceTest {

    @Mock
    private NoticiaRepository repository;

    @InjectMocks
    private NoticiaService service;

    private static Noticia noticia(Long id, LocalDate fecha) {
        Noticia n = new Noticia("Lanzamientos", "Nuevas guitarras", fecha, "Resumen", "Contenido completo", "/img/n.png");
        n.setId(id);
        return n;
    }

    @Test
    @DisplayName("Crear guarda la noticia con los textos sin espacios sobrantes")
    void crear() {
        when(repository.save(any(Noticia.class))).thenAnswer(inv -> inv.getArgument(0));

        NoticiaResponse r = service.crear(new NoticiaRequest(" Eventos ", " Clínica de batería ",
                LocalDate.of(2026, 11, 20), " Resumen ", " Contenido ", "  "));

        assertEquals("Eventos", r.categoria());
        assertEquals("Clínica de batería", r.titulo());
        assertNull(r.imagen());
    }

    @Test
    @DisplayName("Listar devuelve las noticias de la más reciente a la más antigua")
    void listarOrdenaPorFecha() {
        when(repository.findAll()).thenReturn(List.of(
                noticia(1L, LocalDate.of(2026, 1, 10)),
                noticia(2L, LocalDate.of(2026, 11, 1))));

        List<NoticiaResponse> r = service.listar();

        assertEquals(2L, r.get(0).id());
        assertEquals(1L, r.get(1).id());
    }

    @Test
    @DisplayName("PATCH con solo el título cambia el título y conserva el resto")
    void actualizarSoloTitulo() {
        Noticia n = noticia(1L, LocalDate.of(2026, 11, 1));
        when(repository.findById(1L)).thenReturn(Optional.of(n));
        when(repository.save(n)).thenReturn(n);

        NoticiaResponse r = service.actualizar(1L, new NoticiaPatchRequest(null, " Título editado ", null, null, null, null));

        assertEquals("Título editado", r.titulo());
        assertEquals("Lanzamientos", r.categoria());
        assertEquals("Resumen", r.resumen());
        assertEquals("/img/n.png", r.imagen());
    }

    @Test
    @DisplayName("PATCH con imagen vacía quita la imagen de la noticia")
    void actualizarQuitaImagen() {
        Noticia n = noticia(1L, LocalDate.of(2026, 11, 1));
        when(repository.findById(1L)).thenReturn(Optional.of(n));
        when(repository.save(n)).thenReturn(n);

        NoticiaResponse r = service.actualizar(1L, new NoticiaPatchRequest(null, null, null, null, null, ""));

        assertNull(r.imagen());
    }

    @Test
    @DisplayName("Consultar una noticia inexistente lanza RecursoNoEncontrado")
    void obtenerInexistente() {
        when(repository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtener(50L));
    }
}
