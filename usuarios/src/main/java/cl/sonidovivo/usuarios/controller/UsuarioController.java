package cl.sonidovivo.usuarios.controller;

import cl.sonidovivo.usuarios.dto.*;
import cl.sonidovivo.usuarios.security.UsuarioAutenticado;
import cl.sonidovivo.usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuarios", description = "Perfil propio y administración de cuentas (requiere token)")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Operation(summary = "Consultar perfil", description = "Datos de la cuenta que inició sesión.")
    @GetMapping("/perfil")
    public UsuarioResponse perfil(@AuthenticationPrincipal Jwt jwt) {
        return service.obtenerPerfil(UsuarioAutenticado.desde(jwt));
    }

    @Operation(summary = "Editar perfil", description = "PATCH: envíe nombre, telefono, region, comuna O foto.")
    @PatchMapping("/perfil")
    public UsuarioResponse editarPerfil(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody ActualizarPerfilRequest req) {
        return service.actualizarPerfil(UsuarioAutenticado.desde(jwt), req);
    }

    @Operation(summary = "Cambiar contraseña", description = "Requiere la contraseña actual.")
    @ApiResponse(responseCode = "204", description = "Contraseña cambiada")
    @ApiResponse(responseCode = "400", description = "Contraseña actual incorrecta o nueva no válida")
    @PatchMapping("/perfil/password")
    public ResponseEntity<Void> cambiarPassword(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CambiarPasswordRequest req) {
        service.cambiarPassword(UsuarioAutenticado.desde(jwt), req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar cuentas", description = "Acceso: Admin. Filtro opcional ?buscar= por nombre, correo o RUT.")
    @GetMapping
    public List<UsuarioResponse> listar(@Parameter(description = "Texto a buscar") @RequestParam(required = false) String buscar) {
        return service.listar(buscar);
    }

    @Operation(summary = "Consultar cuenta por id", description = "Acceso: Admin.")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{id}")
    public UsuarioResponse obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @Operation(summary = "Crear cuenta desde administración", description = "Acceso: Admin. Permite elegir rol y estado.")
    @ApiResponse(responseCode = "201", description = "Cuenta creada")
    @ApiResponse(responseCode = "409", description = "RUT o correo ya registrado")
    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody CrearUsuarioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    @Operation(summary = "Editar datos de una cuenta",
            description = "PATCH. Acceso: Admin (cualquier cuenta) o Cliente (solo la propia, sin cambiar rol ni activo).")
    @ApiResponse(responseCode = "403", description = "No es su cuenta o intenta cambiar rol/estado sin ser admin")
    @ApiResponse(responseCode = "409", description = "RUT o correo ya registrado, o se quedaría sin administradores")
    @PatchMapping("/{id}")
    public UsuarioResponse actualizar(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id,
                                    @Valid @RequestBody ActualizarUsuarioRequest req) {
        return service.actualizar(UsuarioAutenticado.desde(jwt), id, req);
    }

    @Operation(summary = "Eliminar cuenta", description = "Acceso: Admin o Cliente (solo la propia).")
    @ApiResponse(responseCode = "204", description = "Cuenta eliminada")
    @ApiResponse(responseCode = "403", description = "No es su cuenta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        service.eliminar(UsuarioAutenticado.desde(jwt), id);
        return ResponseEntity.noContent().build();
    }
}
