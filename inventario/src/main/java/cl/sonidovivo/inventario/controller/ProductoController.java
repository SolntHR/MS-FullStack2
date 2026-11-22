package cl.sonidovivo.inventario.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.sonidovivo.inventario.dto.FiltroProductos;
import cl.sonidovivo.inventario.dto.ProductoPatchRequest;
import cl.sonidovivo.inventario.dto.ProductoRequest;
import cl.sonidovivo.inventario.dto.ProductoResponse;
import cl.sonidovivo.inventario.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Catálogo (público) y mantenimiento de productos (Admin)")
public class ProductoController {
    
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar productos", description = "Acceso: todos.")
    @GetMapping
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    @Operation(summary = "Filtrar productos",
            description = "Acceso: todos. Filtros combinables: categoria (uno o varios), texto, marca, precioMin, precioMax, soloDisponibles.")
    @ApiResponse(responseCode = "400", description = "precioMin mayor que precioMax")
    @GetMapping("/filtrar")
    public List<ProductoResponse> filtrar(@ParameterObject @ModelAttribute FiltroProductos filtro) {
        return service.filtrar(filtro);
    }

    @Operation(summary = "Detalle de producto por id", description = "Acceso: todos.")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Detalle de producto por código", description = "Acceso: todos. Ej: /productos/codigo/GA001")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/codigo/{codigo}")
    public ProductoResponse obtenerPorCodigo(@PathVariable String codigo) {
        return service.obtenerPorCodigo(codigo);
    }

    @Operation(summary = "Crear producto", description = "Acceso: Admin.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @ApiResponse(responseCode = "409", description = "El código ya existe")
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    @Operation(summary = "Editar producto", description = "PATCH, solo los campos a cambiar. Acceso: Admin.")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ProductoPatchRequest req) {
        return service.actualizar(id, req);
    }

    @Operation(summary = "Eliminar producto", description = "Acceso: Admin.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
