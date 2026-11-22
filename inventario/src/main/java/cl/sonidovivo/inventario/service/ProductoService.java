package cl.sonidovivo.inventario.service;

import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.sonidovivo.inventario.dto.FiltroProductos;
import cl.sonidovivo.inventario.dto.ProductoPatchRequest;
import cl.sonidovivo.inventario.dto.ProductoRequest;
import cl.sonidovivo.inventario.dto.ProductoResponse;
import cl.sonidovivo.inventario.exception.ConflictoException;
import cl.sonidovivo.inventario.exception.RecursoNoEncontradoException;
import cl.sonidovivo.inventario.exception.SolicitudInvalidaException;
import cl.sonidovivo.inventario.model.Categoria;
import cl.sonidovivo.inventario.model.Producto;
import cl.sonidovivo.inventario.repository.ProductoRepository;

@Service
public class ProductoService {
    
    private final ProductoRepository repository;
    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository repository, CategoriaService categoriaService) {
        this.repository = repository;
        this.categoriaService = categoriaService;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listar() {
        return ordenados(repository.findAll().stream()).map(ProductoResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> filtrar(FiltroProductos f) {
        Set<String> categorias = f.categoria() == null ? Set.of() : f.categoria().stream()
                .map(TextoUtil::normalizar).filter(c -> !c.isEmpty()).collect(Collectors.toSet());
        String texto = TextoUtil.normalizar(f.texto());
        String marca = TextoUtil.normalizar(f.marca());

        if (f.precioMin() != null && f.precioMax() != null && f.precioMin() > f.precioMax()) {
            throw new SolicitudInvalidaException("precioMin no puede ser mayor que precioMax.");
        }

        Stream<Producto> productos = repository.findAll().stream()
                .filter(p -> categorias.isEmpty() || categorias.contains(TextoUtil.normalizar(p.getCategoria().getNombre())))
                .filter(p -> marca.isEmpty() || TextoUtil.normalizar(p.getMarca()).equals(marca))
                .filter(p -> f.precioMin() == null || p.getPrecio() >= f.precioMin())
                .filter(p -> f.precioMax() == null || p.getPrecio() <= f.precioMax())
                .filter(p -> !Boolean.TRUE.equals(f.soloDisponibles()) || p.getStock() > 0)
                .filter(p -> texto.isEmpty() || Stream.of(p.getNombre(), p.getMarca(), p.getModelo(), p.getCodigo())
                        .anyMatch(campo -> TextoUtil.normalizar(campo).contains(texto)));
        return ordenados(productos).map(ProductoResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtener(Long id) {
        return ProductoResponse.desde(buscar(id));
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorCodigo(String codigo) {
        return repository.findByCodigoIgnoreCase(codigo.trim())
                .map(ProductoResponse::desde)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con código " + codigo + "."));
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest req) {
        String codigo = req.codigo().trim().toUpperCase();
        if (repository.existsByCodigoIgnoreCase(codigo)) {
            throw new ConflictoException("Este código ya existe.");
        }
        Producto p = new Producto();
        p.setCodigo(codigo);
        p.setNombre(req.nombreproducto().trim());
        p.setCategoria(resolverCategoria(req.idCategoria(), req.categoria(), true));
        p.setMarca(req.marca().trim());
        p.setModelo(req.modelo().trim());
        p.setPrecio(req.precio());
        p.setStock(req.stock());
        p.setDescripcion(TextoUtil.vacioANull(req.descripcion()));
        p.setImagen(TextoUtil.vacioANull(req.imagen()));
        return ProductoResponse.desde(repository.save(p));
    }

    @Transactional
    public ProductoResponse actualizar(Long id, ProductoPatchRequest req) {
        Producto p = buscar(id);
        if (req.codigo() != null) {
            String codigo = req.codigo().trim().toUpperCase();
            repository.findByCodigoIgnoreCase(codigo).filter(otro -> !otro.getId().equals(id))
                    .ifPresent(otro -> { throw new ConflictoException("Este código ya existe."); });
            p.setCodigo(codigo);
        }
        if (req.nombreproducto() != null) p.setNombre(req.nombreproducto().trim());
        Categoria categoria = resolverCategoria(req.idCategoria(), req.categoria(), false);
        if (categoria != null) p.setCategoria(categoria);
        if (req.marca() != null) p.setMarca(req.marca().trim());
        if (req.modelo() != null) p.setModelo(req.modelo().trim());
        if (req.precio() != null) p.setPrecio(req.precio());
        if (req.stock() != null) p.setStock(req.stock());
        if (req.descripcion() != null) p.setDescripcion(TextoUtil.vacioANull(req.descripcion()));
        if (req.imagen() != null) p.setImagen(TextoUtil.vacioANull(req.imagen()));
        return ProductoResponse.desde(repository.save(p));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private Producto buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id " + id + "."));
    }

    private Categoria resolverCategoria(Long idCategoria, String nombreCategoria, boolean obligatoria) {
        if (idCategoria != null) {
            return categoriaService.buscar(idCategoria);
        }
        if (nombreCategoria != null && !nombreCategoria.isBlank()) {
            return categoriaService.buscarPorNombre(nombreCategoria);
        }
        if (obligatoria) {
            throw new SolicitudInvalidaException("Debe seleccionar una categoría (idCategoria o categoria).");
        }
        return null;
    }

    private static Stream<Producto> ordenados(Stream<Producto> productos) {
        return productos.sorted(Comparator.comparing(Producto::getId));
    }
}
