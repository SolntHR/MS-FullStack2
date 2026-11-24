package cl.sonidovivo.contacto.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.sonidovivo.contacto.dto.ContactoRequest;
import cl.sonidovivo.contacto.dto.ContactoResponse;
import cl.sonidovivo.contacto.dto.EstadoContactoRequest;
import cl.sonidovivo.contacto.dto.RespuestaContactoRequest;
import cl.sonidovivo.contacto.exception.RecursoNoEncontradoException;
import cl.sonidovivo.contacto.model.Contacto;
import cl.sonidovivo.contacto.model.EstadoContacto;
import cl.sonidovivo.contacto.repository.ContactoRepository;

@Service
public class ContactoService {
    private final ContactoRepository repository;

    public ContactoService(ContactoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ContactoResponse> listar() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Contacto::getFechaCreacion).reversed())
                .map(ContactoResponse::desde)
                .toList();
    }

    /**
     * Registra un mensaje. Si quien escribe tiene sesión (correoSesion distinto de null),
     * se usa el correo de su cuenta y no el del formulario: así nadie puede enviar mensajes
     * a nombre de otra cuenta registrada.
     */
    @Transactional
    public ContactoResponse crear(ContactoRequest req, String correoSesion) {
        String correo = correoSesion != null ? correoSesion : req.correo().trim();
        Contacto contacto = new Contacto(req.nombre().trim(), correo, req.mensaje().trim());
        return ContactoResponse.desde(repository.save(contacto));
    }

    @Transactional
    public ContactoResponse actualizarEstado(Long id, EstadoContactoRequest req) {
        Contacto contacto = buscar(id);
        contacto.setEstado(req.estado());
        return ContactoResponse.desde(repository.save(contacto));
    }

    /** El administrador responde: se guarda el texto, la fecha y el mensaje pasa a ATENDIDO */
    @Transactional
    public ContactoResponse responder(Long id, RespuestaContactoRequest req) {
        Contacto contacto = buscar(id);
        contacto.setRespuesta(req.respuesta().trim());
        contacto.setFechaRespuesta(LocalDateTime.now());
        contacto.setEstado(EstadoContacto.ATENDIDO);
        return ContactoResponse.desde(repository.save(contacto));
    }

    /** Mensajes del usuario con sesión. El correo viene del token, no de un parámetro */
    @Transactional(readOnly = true)
    public List<ContactoResponse> misMensajes(String correo) {
        return repository.findByCorreoIgnoreCaseOrderByFechaCreacionDesc(correo).stream()
                .map(ContactoResponse::desde)
                .toList();
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private Contacto buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Contacto no encontrado con id " + id + "."));
    }
}
