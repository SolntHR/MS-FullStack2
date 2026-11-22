package cl.sonidovivo.carrito.service;

import cl.sonidovivo.carrito.cliente.InventarioCliente;
import cl.sonidovivo.carrito.cliente.ProductoInventario;
import cl.sonidovivo.carrito.dto.AgregarItemRequest;
import cl.sonidovivo.carrito.dto.CarritoResponse;
import cl.sonidovivo.carrito.dto.ItemCarritoResponse;
import cl.sonidovivo.carrito.exception.ConflictoException;
import cl.sonidovivo.carrito.exception.RecursoNoEncontradoException;
import cl.sonidovivo.carrito.model.ItemCarrito;
import cl.sonidovivo.carrito.repository.ItemCarritoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoService {

    private final ItemCarritoRepository repository;
    private final InventarioCliente inventario;

    public CarritoService(ItemCarritoRepository repository, InventarioCliente inventario) {
        this.repository = repository;
        this.inventario = inventario;
    }

    @Transactional(readOnly = true)
    public CarritoResponse obtener(Long idUsuario) {
        return CarritoResponse.desde(repository.findByIdUsuarioOrderByIdAsc(idUsuario).stream()
                .map(ItemCarritoResponse::desde)
                .toList());
    }

    @Transactional
    public CarritoResponse agregar(Long idUsuario, AgregarItemRequest req) {
        ProductoInventario producto = inventario.obtenerPorCodigo(req.codigo().trim());

        ItemCarrito item = repository.findByIdUsuarioAndCodigoIgnoreCase(idUsuario, producto.codigo())
                .orElseGet(() -> {
                    ItemCarrito nuevo = new ItemCarrito();
                    nuevo.setIdUsuario(idUsuario);
                    nuevo.setCantidad(0);
                    return nuevo;
                });

        int cantidadFinal = item.getCantidad() + req.cantidad();
        validarStock(producto, cantidadFinal);

        copiarDatosProducto(item, producto);
        item.setCantidad(cantidadFinal);
        repository.save(item);
        return obtener(idUsuario);
    }

    @Transactional
    public CarritoResponse actualizarCantidad(Long idUsuario, String codigo, int cantidad) {
        ItemCarrito item = buscar(idUsuario, codigo);
        ProductoInventario producto = inventario.obtenerPorCodigo(item.getCodigo());
        validarStock(producto, cantidad);

        copiarDatosProducto(item, producto);
        item.setCantidad(cantidad);
        repository.save(item);
        return obtener(idUsuario);
    }

    @Transactional
    public CarritoResponse quitar(Long idUsuario, String codigo) {
        repository.delete(buscar(idUsuario, codigo));
        return obtener(idUsuario);
    }

    @Transactional
    public void vaciar(Long idUsuario) {
        repository.deleteByIdUsuario(idUsuario);
    }

    private ItemCarrito buscar(Long idUsuario, String codigo) {
        return repository.findByIdUsuarioAndCodigoIgnoreCase(idUsuario, codigo.trim())
                .orElseThrow(() -> new RecursoNoEncontradoException("El producto " + codigo + " no está en su carrito."));
    }

    private void validarStock(ProductoInventario producto, int cantidad) {
        if (producto.stock() == null || producto.stock() <= 0) {
            throw new ConflictoException(producto.nombreproducto() + " está agotado.");
        }
        if (cantidad > producto.stock()) {
            throw new ConflictoException("Solo hay " + producto.stock() + " unidad(es) disponibles de "
                    + producto.nombreproducto() + ".");
        }
    }

    private void copiarDatosProducto(ItemCarrito item, ProductoInventario producto) {
        item.setIdProducto(producto.id());
        item.setCodigo(producto.codigo());
        item.setNombre(producto.nombreproducto());
        item.setMarca(producto.marca());
        item.setModelo(producto.modelo());
        item.setPrecio(producto.precio());
        item.setImagen(producto.imagen());
    }
}
