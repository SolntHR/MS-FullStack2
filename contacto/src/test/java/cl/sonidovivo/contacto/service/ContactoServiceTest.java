package cl.sonidovivo.contacto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.sonidovivo.contacto.dto.ContactoRequest;
import cl.sonidovivo.contacto.dto.ContactoResponse;
import cl.sonidovivo.contacto.dto.RespuestaContactoRequest;
import cl.sonidovivo.contacto.exception.RecursoNoEncontradoException;
import cl.sonidovivo.contacto.model.Contacto;
import cl.sonidovivo.contacto.model.EstadoContacto;
import cl.sonidovivo.contacto.repository.ContactoRepository;

@ExtendWith(MockitoExtension.class)
class ContactoServiceTest {

    @Mock
    private ContactoRepository repository;

    @InjectMocks
    private ContactoService service;

    private static Contacto mensaje(Long id) {
        Contacto c = new Contacto("Ana Pérez", "ana@correo.cl", "¿Tienen stock del bajo Ibanez?");
        c.setId(id);
        return c;
    }

    @Test
    @DisplayName("Un visitante envía un mensaje: se guarda con su correo y estado PENDIENTE")
    void crearSinSesion() {
        when(repository.save(any(Contacto.class))).thenAnswer(inv -> inv.getArgument(0));

        ContactoResponse r = service.crear(new ContactoRequest("  Ana Pérez ", "ana@correo.cl", " Hola "), null);

        assertEquals("Ana Pérez", r.nombre());
        assertEquals("ana@correo.cl", r.correo());
        assertEquals("Hola", r.mensaje());
        assertEquals(EstadoContacto.PENDIENTE, r.estado());
    }

    @Test
    @DisplayName("Con sesión, el mensaje se registra con el correo de la cuenta y no con el del formulario")
    void crearConSesionUsaCorreoDelToken() {
        when(repository.save(any(Contacto.class))).thenAnswer(inv -> inv.getArgument(0));

        ContactoResponse r = service.crear(new ContactoRequest("Ana", "otro@correo.cl", "Consulta"), "ana@correo.cl");

        assertEquals("ana@correo.cl", r.correo());
    }

    @Test
    @DisplayName("Responder guarda el texto, la fecha y marca el mensaje como ATENDIDO")
    void responder() {
        Contacto c = mensaje(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(c));
        when(repository.save(c)).thenReturn(c);

        ContactoResponse r = service.responder(1L, new RespuestaContactoRequest("  Sí, quedan 2 unidades.  "));

        assertEquals("Sí, quedan 2 unidades.", r.respuesta());
        assertEquals(EstadoContacto.ATENDIDO, r.estado());
        assertNotNull(r.fechaRespuesta());
    }

    @Test
    @DisplayName("Responder un mensaje que no existe lanza RecursoNoEncontrado y no guarda nada")
    void responderInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> service.responder(99L, new RespuestaContactoRequest("Hola")));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Mis mensajes consulta solo los mensajes del correo recibido")
    void misMensajes() {
        when(repository.findByCorreoIgnoreCaseOrderByFechaCreacionDesc("ana@correo.cl")).thenReturn(List.of(mensaje(1L), mensaje(2L)));

        List<ContactoResponse> r = service.misMensajes("ana@correo.cl");

        assertEquals(2, r.size());
        verify(repository).findByCorreoIgnoreCaseOrderByFechaCreacionDesc("ana@correo.cl");
    }
}
