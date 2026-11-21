package cl.sonidovivo.usuarios.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import cl.sonidovivo.usuarios.model.Usuario;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    private final long duracionSegundos;

    public TokenService(JwtEncoder encoder, @Value("${jwt.expiracion-segundos:7200}") long duracionSegundos) {
        this.encoder = encoder;
        this.duracionSegundos = duracionSegundos;
    }

    /** El "sub" del token es el id del usuario y el claim "rol" es CLIENTE o ADMINISTRADOR */
    public String generarToken(Usuario usuario) {
        Instant ahora = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("sonido-vivo")
                .issuedAt(ahora)
                .expiresAt(ahora.plusSeconds(duracionSegundos))
                .subject(String.valueOf(usuario.getId()))
                .claim("rol", usuario.getRol().name())
                .claim("correo", usuario.getCorreo())
                .claim("nombre", usuario.getNombre())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public long getDuracionSegundos() {
        return duracionSegundos;
    }
}
