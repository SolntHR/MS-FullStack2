package cl.sonidovivo.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.sonidovivo.usuarios.dto.LoginRequest;
import cl.sonidovivo.usuarios.dto.RegistroRequest;
import cl.sonidovivo.usuarios.dto.UsuarioResponse;
import cl.sonidovivo.usuarios.exception.AccesoDenegadoException;
import cl.sonidovivo.usuarios.exception.ConflictoException;
import cl.sonidovivo.usuarios.exception.CredencialesInvalidasException;
import cl.sonidovivo.usuarios.model.Rol;
import cl.sonidovivo.usuarios.model.Usuario;
import cl.sonidovivo.usuarios.repository.UsuarioRepository;
import cl.sonidovivo.usuarios.security.TokenService;
import cl.sonidovivo.usuarios.security.UsuarioAutenticado;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UsuarioService service;

    private static RegistroRequest registro(String correo) {
        return new RegistroRequest("12.345.678-5", "Ana Pérez", correo, "Clave1234", null, null, null);
    }

    private static Usuario usuario(Long id, Rol rol, boolean activo) {
        Usuario u = new Usuario();
        u.setId(id);
        u.setRut("12345678-5");
        u.setNombre("Ana Pérez");
        u.setCorreo("ana@correo.cl");
        u.setPassword("hash");
        u.setRol(rol);
        u.setActivo(activo);
        return u;
    }

    @Test
    @DisplayName("Registrar crea un Cliente activo, con el correo en minúsculas y la contraseña cifrada")
    void registrarCliente() {
        when(passwordEncoder.encode("Clave1234")).thenReturn("hash-bcrypt");
        when(repository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        UsuarioResponse r = service.registrar(registro("Ana@Correo.CL"));

        assertEquals("ana@correo.cl", r.correo());
        assertEquals(Rol.CLIENTE, r.rol());
        assertEquals("12345678-5", r.rut());
        verify(repository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Registrar con un correo ya usado lanza Conflicto y no guarda")
    void registrarCorreoDuplicado() {
        when(repository.existsByRut(anyString())).thenReturn(false);
        when(repository.existsByCorreoIgnoreCase("ana@correo.cl")).thenReturn(true);

        assertThrows(ConflictoException.class, () -> service.registrar(registro("ana@correo.cl")));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Login con contraseña incorrecta lanza CredencialesInvalidas")
    void loginPasswordIncorrecta() {
        when(repository.findByCorreoIgnoreCase("ana@correo.cl")).thenReturn(Optional.of(usuario(1L, Rol.CLIENTE, true)));
        when(passwordEncoder.matches("Mala1234", "hash")).thenReturn(false);

        assertThrows(CredencialesInvalidasException.class,
                () -> service.login(new LoginRequest("ana@correo.cl", "Mala1234")));
    }

    @Test
    @DisplayName("Login de una cuenta desactivada lanza AccesoDenegado y no genera token")
    void loginCuentaDesactivada() {
        when(repository.findByCorreoIgnoreCase("ana@correo.cl")).thenReturn(Optional.of(usuario(1L, Rol.CLIENTE, false)));
        when(passwordEncoder.matches("Clave1234", "hash")).thenReturn(true);

        assertThrows(AccesoDenegadoException.class,
                () -> service.login(new LoginRequest("ana@correo.cl", "Clave1234")));
        verify(tokenService, never()).generarToken(any());
    }

    @Test
    @DisplayName("No se puede eliminar al último administrador activo")
    void eliminarUltimoAdmin() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario(1L, Rol.ADMINISTRADOR, true)));
        when(repository.countByRolAndActivoTrue(Rol.ADMINISTRADOR)).thenReturn(1L);

        assertThrows(ConflictoException.class, () -> service.eliminar(new UsuarioAutenticado(1L, true), 1L));
        verify(repository, never()).delete(any());
    }
}
