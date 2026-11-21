package cl.sonidovivo.usuarios.security;

import org.springframework.security.oauth2.jwt.Jwt;

public record UsuarioAutenticado (Long id, boolean esAdmin) {

    public static UsuarioAutenticado desde(Jwt jwt) {
        return new UsuarioAutenticado(Long.valueOf(jwt.getSubject()), "ADMINISTRADOR".equals(jwt.getClaimAsString("rol")));
    }
}