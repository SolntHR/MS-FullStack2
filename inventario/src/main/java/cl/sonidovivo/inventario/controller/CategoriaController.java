package cl.sonidovivo.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.sonidovivo.inventario.dto.CategoriaPatchRequest;
import cl.sonidovivo.inventario.dto.CategoriaRequest;
import cl.sonidovivo.inventario.dto.CategoriaResponse;
import cl.sonidovivo.inventario.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorías", description = "Categorías del catálogo")
public class CategoriaController {
    
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar categorías", description = "Acceso: todos. Incluye la cantidad de productos de cada una.")
    @GetMapping
    public List<CategoriaResponse> listar() {
        return service.listar();
    }

    @Operation(summary = "Consultar categoría por id", description = "Acceso: todos.")
    @GetMapping("/{id}")
    public CategoriaResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Crear categoría", description = "Acceso: Admin.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Categoría creada")
    @ApiResponse(responseCode = "409", description = "Ya existe una categoría con ese nombre")
    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    @Operation(summary = "Editar categoría", description = "PATCH, solo los campos a cambiar. Acceso: Admin.")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}")
    public CategoriaResponse actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaPatchRequest req) {
        return service.actualizar(id, req);
    }

    @Operation(summary = "Eliminar categoría", description = "Acceso: Admin. Solo si no tiene productos.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada")
    @ApiResponse(responseCode = "409", description = "La categoría tiene productos")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
