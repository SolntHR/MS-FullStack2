package cl.sonidovivo.noticias.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.sonidovivo.noticias.dto.NoticiaPatchRequest;
import cl.sonidovivo.noticias.dto.NoticiaRequest;
import cl.sonidovivo.noticias.dto.NoticiaResponse;
import cl.sonidovivo.noticias.service.NoticiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/noticias")
@Tag(name = "Noticias", description = "Publicaciones del blog Sonido Vivo")
public class NoticiaController {
    private final NoticiaService service;

    public NoticiaController(NoticiaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar noticias", description = "Acceso: público. Ordenadas por fecha descendente.")
    @GetMapping
    public List<NoticiaResponse> listar() {
        return service.listar();
    }

    @Operation(summary = "Consultar noticia por id", description = "Acceso: público.")
    @ApiResponse(responseCode = "404", description = "Noticia no encontrada")
    @GetMapping("/{id}")
    public NoticiaResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Crear noticia", description = "Acceso: Administrador.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Noticia creada")
    @PostMapping
    public ResponseEntity<NoticiaResponse> crear(@Valid @RequestBody NoticiaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    @Operation(summary = "Editar noticia", description = "PATCH: solo se actualizan los campos enviados. Acceso: Administrador.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "404", description = "Noticia no encontrada")
    @PatchMapping("/{id}")
    public NoticiaResponse actualizar(@PathVariable Long id, @Valid @RequestBody NoticiaPatchRequest req) {
        return service.actualizar(id, req);
    }

    @Operation(summary = "Eliminar noticia", description = "Acceso: Administrador.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "204", description = "Noticia eliminada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
