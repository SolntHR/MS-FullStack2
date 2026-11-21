package cl.sonidovivo.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.sonidovivo.usuarios.model.Rol;
import cl.sonidovivo.usuarios.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoIgnoreCase(String correo);
    Optional<Usuario> findByRut(String rut);
    boolean existsByCorreoIgnoreCase(String correo);
    boolean existsByRut(String rut);
    long countByRolAndActivoTrue(Rol rol);
}
