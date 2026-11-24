package cl.sonidovivo.contacto.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.sonidovivo.contacto.dto.ContactoRequest;
import cl.sonidovivo.contacto.dto.ContactoResponse;
import cl.sonidovivo.contacto.dto.EstadoContactoRequest;
import cl.sonidovivo.contacto.exception.RecursoNoEncontradoException;
import cl.sonidovivo.contacto.model.Contacto;
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

    @Transactional
    public ContactoResponse crear(ContactoRequest req) {
        Contacto contacto = new Contacto(req.nombre().trim(), req.correo().trim(), req.mensaje().trim());
        return ContactoResponse.desde(repository.save(contacto));
    }

    @Transactional
    public ContactoResponse actualizarEstado(Long id, EstadoContactoRequest req) {
        Contacto contacto = buscar(id);
        contacto.setEstado(req.estado());
        return ContactoResponse.desde(repository.save(contacto));
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
