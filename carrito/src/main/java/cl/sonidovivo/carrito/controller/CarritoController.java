package cl.sonidovivo.carrito.controller;

import cl.sonidovivo.carrito.dto.ActualizarCantidadRequest;
import cl.sonidovivo.carrito.dto.AgregarItemRequest;
import cl.sonidovivo.carrito.dto.CarritoResponse;
import cl.sonidovivo.carrito.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Carrito", description = "Carrito de compras del usuario que inició sesión (requiere token)")
public class CarritoController {

    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    private static Long idUsuario(Jwt jwt) {
        return Long.valueOf(jwt.getSubject());
    }

    @Operation(summary = "Consultar mi carrito", description = "Productos, cantidad total y total a pagar.")
    @GetMapping
    public CarritoResponse obtener(@AuthenticationPrincipal Jwt jwt) {
        return service.obtener(idUsuario(jwt));
    }

    @Operation(summary = "Agregar un producto",
            description = "Si ya está en el carrito, suma la cantidad. Valida el stock con el microservicio inventario.")
    @ApiResponse(responseCode = "200", description = "Carrito actualizado")
    @ApiResponse(responseCode = "404", description = "El producto no existe")
    @ApiResponse(responseCode = "409", description = "No hay stock suficiente")
    @ApiResponse(responseCode = "503", description = "Inventario no disponible")
    @PostMapping("/items")
    public CarritoResponse agregar(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody AgregarItemRequest req) {
        return service.agregar(idUsuario(jwt), req);
    }

    @Operation(summary = "Actualizar la cantidad de un producto", description = "PATCH con la nueva cantidad.")
    @ApiResponse(responseCode = "404", description = "El producto no está en el carrito")
    @ApiResponse(responseCode = "409", description = "No hay stock suficiente")
    @PatchMapping("/items/{codigo}")
    public CarritoResponse actualizarCantidad(@AuthenticationPrincipal Jwt jwt, @PathVariable String codigo, @Valid @RequestBody ActualizarCantidadRequest req) {
        return service.actualizarCantidad(idUsuario(jwt), codigo, req.cantidad());
    }

    @Operation(summary = "Quitar un producto del carrito")
    @ApiResponse(responseCode = "404", description = "El producto no está en el carrito")
    @DeleteMapping("/items/{codigo}")
    public CarritoResponse quitar(@AuthenticationPrincipal Jwt jwt, @PathVariable String codigo) {
        return service.quitar(idUsuario(jwt), codigo);
    }

    @Operation(summary = "Vaciar el carrito")
    @ApiResponse(responseCode = "204", description = "Carrito vaciado")
    @DeleteMapping
    public ResponseEntity<Void> vaciar(@AuthenticationPrincipal Jwt jwt) {
        service.vaciar(idUsuario(jwt));
        return ResponseEntity.noContent().build();
    }
}
