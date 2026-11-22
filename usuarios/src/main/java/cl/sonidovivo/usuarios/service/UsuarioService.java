package cl.sonidovivo.usuarios.service;

import java.text.Normalizer;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.sonidovivo.usuarios.dto.ActualizarPerfilRequest;
import cl.sonidovivo.usuarios.dto.ActualizarUsuarioRequest;
import cl.sonidovivo.usuarios.dto.CambiarPasswordRequest;
import cl.sonidovivo.usuarios.dto.CrearUsuarioRequest;
import cl.sonidovivo.usuarios.dto.LoginRequest;
import cl.sonidovivo.usuarios.dto.LoginResponse;
import cl.sonidovivo.usuarios.dto.RegistroRequest;
import cl.sonidovivo.usuarios.dto.UsuarioResponse;
import cl.sonidovivo.usuarios.exception.AccesoDenegadoException;
import cl.sonidovivo.usuarios.exception.ConflictoException;
import cl.sonidovivo.usuarios.exception.CredencialesInvalidasException;
import cl.sonidovivo.usuarios.exception.RecursoNoEncontradoException;
import cl.sonidovivo.usuarios.exception.SolicitudInvalidaException;
import cl.sonidovivo.usuarios.model.Rol;
import cl.sonidovivo.usuarios.model.Usuario;
import cl.sonidovivo.usuarios.repository.UsuarioRepository;
import cl.sonidovivo.usuarios.security.TokenService;
import cl.sonidovivo.usuarios.security.UsuarioAutenticado;
import cl.sonidovivo.usuarios.validacion.ValidadorRut;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
    
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public UsuarioResponse registrar(RegistroRequest req) {
        Usuario usuario = nuevoUsuario(req.rut(), req.nombre(), req.correo(), req.password(),
                Rol.CLIENTE, true, req.region(), req.comuna(), req.telefono());
        log.info("Cuenta registrada con id {}", usuario.getId());
        return UsuarioResponse.desde(usuario);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest req) {
        Usuario usuario = repository.findByCorreoIgnoreCase(req.correo().trim())
                .filter(u -> passwordEncoder.matches(req.password(), u.getPassword()))
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos."));
        if (!usuario.isActivo()) {
            throw new AccesoDenegadoException("La cuenta está desactivada. Contacte a la tienda.");
        }
        String token = tokenService.generarToken(usuario);
        return new LoginResponse(token, "Bearer", tokenService.getDuracionSegundos(), UsuarioResponse.desde(usuario));
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPerfil(UsuarioAutenticado actual) {
        return UsuarioResponse.desde(buscar(actual.id()));
    }

    @Transactional
    public UsuarioResponse actualizarPerfil(UsuarioAutenticado actual, ActualizarPerfilRequest req) {
        Usuario usuario = buscar(actual.id());
        if (req.nombre() != null) usuario.setNombre(req.nombre().trim());
        if (req.telefono() != null) usuario.setTelefono(vacioANull(req.telefono()));
        if (req.region() != null) usuario.setRegion(vacioANull(req.region()));
        if (req.comuna() != null) usuario.setComuna(vacioANull(req.comuna()));
        if (req.foto() != null) usuario.setFoto(vacioANull(req.foto()));
        return UsuarioResponse.desde(repository.save(usuario));
    }

    @Transactional
    public void cambiarPassword(UsuarioAutenticado actual, CambiarPasswordRequest req) {
        Usuario usuario = buscar(actual.id());
        if (!passwordEncoder.matches(req.actual(), usuario.getPassword())) {
            throw new SolicitudInvalidaException("La contraseña actual no es correcta.");
        }
        if (passwordEncoder.matches(req.nueva(), usuario.getPassword())) {
            throw new SolicitudInvalidaException("La nueva contraseña debe ser distinta a la actual.");
        }
        usuario.setPassword(passwordEncoder.encode(req.nueva()));
        repository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar(String buscar) {
        String texto = normalizarTexto(buscar);
        String rutBuscado = ValidadorRut.limpiar(buscar);
        return repository.findAll().stream()
                .filter(u -> texto.isEmpty()
                        || normalizarTexto(u.getNombre()).contains(texto)
                        || normalizarTexto(u.getCorreo()).contains(texto)
                        || (!rutBuscado.isEmpty() && ValidadorRut.limpiar(u.getRut()).contains(rutBuscado)))
                .map(UsuarioResponse::desde)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        return UsuarioResponse.desde(buscar(id));
    }

    @Transactional
    public UsuarioResponse crear(CrearUsuarioRequest req) {
        Rol rol = req.rol() == null ? Rol.CLIENTE : req.rol();
        boolean activo = req.activo() == null || req.activo();
        return UsuarioResponse.desde(nuevoUsuario(req.rut(), req.nombre(), req.correo(), req.password(),
                rol, activo, req.region(), req.comuna(), req.telefono()));
    }

    @Transactional
    public UsuarioResponse actualizar(UsuarioAutenticado actual, Long id, ActualizarUsuarioRequest req) {
        verificarAdminOPropia(actual, id);
        Usuario usuario = buscar(id);

        if (!actual.esAdmin() && (req.rol() != null || req.activo() != null)) {
            throw new AccesoDenegadoException("Solo un administrador puede cambiar el rol o el estado de una cuenta.");
        }

        if (req.rut() != null) {
            String rut = ValidadorRut.normalizar(req.rut());
            repository.findByRut(rut).filter(otro -> !otro.getId().equals(id))
                    .ifPresent(otro -> { throw new ConflictoException("Este RUT ya está registrado."); });
            usuario.setRut(rut);
        }
        if (req.correo() != null) {
            String correo = req.correo().trim().toLowerCase();
            repository.findByCorreoIgnoreCase(correo).filter(otro -> !otro.getId().equals(id))
                    .ifPresent(otro -> { throw new ConflictoException("Este correo ya está registrado."); });
            usuario.setCorreo(correo);
        }
        if (req.nombre() != null) usuario.setNombre(req.nombre().trim());
        if (req.password() != null) usuario.setPassword(passwordEncoder.encode(req.password()));
        if (req.region() != null) usuario.setRegion(vacioANull(req.region()));
        if (req.comuna() != null) usuario.setComuna(vacioANull(req.comuna()));
        if (req.telefono() != null) usuario.setTelefono(vacioANull(req.telefono()));
        if (req.foto() != null) usuario.setFoto(vacioANull(req.foto()));

        boolean pierdeAdmin = usuario.getRol() == Rol.ADMINISTRADOR && usuario.isActivo()
                && ((req.rol() != null && req.rol() != Rol.ADMINISTRADOR) || Boolean.FALSE.equals(req.activo()));
        if (pierdeAdmin) {
            verificarQueQuedeOtroAdmin();
        }
        if (req.rol() != null) usuario.setRol(req.rol());
        if (req.activo() != null) usuario.setActivo(req.activo());

        return UsuarioResponse.desde(repository.save(usuario));
    }

    @Transactional
    public void eliminar(UsuarioAutenticado actual, Long id) {
        verificarAdminOPropia(actual, id);
        Usuario usuario = buscar(id);
        if (usuario.getRol() == Rol.ADMINISTRADOR && usuario.isActivo()) {
            verificarQueQuedeOtroAdmin();
        }
        repository.delete(usuario);
        log.info("Cuenta {} eliminada", id);
    }

    private Usuario buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id " + id + "."));
    }

    private void verificarAdminOPropia(UsuarioAutenticado actual, Long id) {
        if (!actual.esAdmin() && !actual.id().equals(id)) {
            throw new AccesoDenegadoException("Solo puede modificar o eliminar su propia cuenta.");
        }
    }

    private void verificarQueQuedeOtroAdmin() {
        if (repository.countByRolAndActivoTrue(Rol.ADMINISTRADOR) <= 1) {
            throw new ConflictoException("Debe existir al menos un administrador activo.");
        }
    }

    private Usuario nuevoUsuario(String rut, String nombre, String correo, String password, Rol rol, boolean activo,
                                String region, String comuna, String telefono) {
        String rutNormalizado = ValidadorRut.normalizar(rut);
        String correoNormalizado = correo.trim().toLowerCase();
        if (repository.existsByRut(rutNormalizado)) {
            throw new ConflictoException("Este RUT ya está registrado.");
        }
        if (repository.existsByCorreoIgnoreCase(correoNormalizado)) {
            throw new ConflictoException("Este correo ya está registrado.");
        }
        Usuario u = new Usuario();
        u.setRut(rutNormalizado);
        u.setNombre(nombre.trim());
        u.setCorreo(correoNormalizado);
        u.setPassword(passwordEncoder.encode(password));
        u.setRol(rol);
        u.setActivo(activo);
        u.setRegion(vacioANull(region));
        u.setComuna(vacioANull(comuna));
        u.setTelefono(vacioANull(telefono));
        return repository.save(u);
    }

    private static String vacioANull(String valor) {
        if (valor == null) return null;
        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    static String normalizarTexto(String texto) {
        if (texto == null) return "";
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase().trim();
    }


}
