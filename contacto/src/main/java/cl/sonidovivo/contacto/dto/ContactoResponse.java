package cl.sonidovivo.contacto.dto;

import java.time.LocalDateTime;

import cl.sonidovivo.contacto.model.Contacto;
import cl.sonidovivo.contacto.model.EstadoContacto;

public record ContactoResponse(Long id, String nombre, String correo, String mensaje,
        EstadoContacto estado, LocalDateTime fechaCreacion) {
    public static ContactoResponse desde(Contacto contacto) {
        return new ContactoResponse(contacto.getId(), contacto.getNombre(), contacto.getCorreo(),
                contacto.getMensaje(), contacto.getEstado(), contacto.getFechaCreacion());
    }
}
