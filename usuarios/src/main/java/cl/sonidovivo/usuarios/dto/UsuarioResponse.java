package cl.sonidovivo.usuarios.dto;

import java.time.LocalDateTime;

import cl.sonidovivo.usuarios.model.Rol;
import cl.sonidovivo.usuarios.model.Usuario;

public record UsuarioResponse(
        Long id,
        String rut,
        String nombre,
        String correo,
        Rol rol,
        boolean activo,
        String region,
        String comuna,
        String telefono,
        String foto,
        LocalDateTime fechaCreacion
) {
    public static UsuarioResponse desde(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getRut(), u.getNombre(), u.getCorreo(), u.getRol(), u.isActivo(),
                u.getRegion(), u.getComuna(), u.getTelefono(), u.getFoto(), u.getFechaCreacion());
    }
}

