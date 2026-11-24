package cl.sonidovivo.contacto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.sonidovivo.contacto.dto.ContactoRequest;
import cl.sonidovivo.contacto.dto.ContactoResponse;
import cl.sonidovivo.contacto.dto.EstadoContactoRequest;
import cl.sonidovivo.contacto.dto.RespuestaContactoRequest;
import cl.sonidovivo.contacto.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contactos")
@Tag(name = "Contacto", description = "Recepción y gestión de mensajes de contacto")
public class ContactoController {
    private final ContactoService service;

    public ContactoController(ContactoService service) {
        this.service = service;
    }

    /** Correo de la cuenta según el token, o null si quien llama no tiene sesión */
    private static String correoDe(Jwt jwt) {
        return jwt == null ? null : jwt.getClaimAsString("correo");
    }

    @Operation(summary = "Enviar un mensaje de contacto",
            description = "Acceso: público. Con sesión, se registra con el correo de la cuenta.")
    @ApiResponse(responseCode = "201", description = "Mensaje recibido")
    @PostMapping
    public ResponseEntity<ContactoResponse> crear(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody ContactoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req, correoDe(jwt)));
    }

    @Operation(summary = "Listar mensajes", description = "Acceso: Administrador.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public List<ContactoResponse> listar() {
        return service.listar();
    }

    @Operation(summary = "Mis mensajes", description = "Mensajes enviados con el correo de la cuenta, con su estado y respuesta. Acceso: usuario con sesión.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/mios")
    public List<ContactoResponse> misMensajes(@AuthenticationPrincipal Jwt jwt) {
        return service.misMensajes(correoDe(jwt));
    }

    @Operation(summary = "Actualizar el estado de un mensaje", description = "PATCH con {estado}. Acceso: Administrador.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    @PatchMapping("/{id}")
    public ContactoResponse actualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoContactoRequest req) {
        return service.actualizarEstado(id, req);
    }

    @Operation(summary = "Responder un mensaje", description = "Guarda la respuesta y marca el mensaje como ATENDIDO. Acceso: Administrador.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "400", description = "Respuesta vacía o demasiado larga")
    @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    @PatchMapping("/{id}/respuesta")
    public ContactoResponse responder(@PathVariable Long id, @Valid @RequestBody RespuestaContactoRequest req) {
        return service.responder(id, req);
    }

    @Operation(summary = "Eliminar un mensaje", description = "Acceso: Administrador.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "204", description = "Mensaje eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
