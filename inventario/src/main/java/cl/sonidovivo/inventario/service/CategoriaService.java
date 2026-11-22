package cl.sonidovivo.inventario.service;

import org.springframework.stereotype.Service;

import cl.sonidovivo.inventario.dto.CategoriaPatchRequest;
import cl.sonidovivo.inventario.dto.CategoriaRequest;
import cl.sonidovivo.inventario.dto.CategoriaResponse;
import cl.sonidovivo.inventario.exception.ConflictoException;
import cl.sonidovivo.inventario.exception.RecursoNoEncontradoException;
import cl.sonidovivo.inventario.model.Categoria;
import cl.sonidovivo.inventario.repository.CategoriaRepository;
import cl.sonidovivo.inventario.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class CategoriaService {
    
    private final CategoriaRepository repository;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository repository, ProductoRepository productoRepository) {
        this.repository = repository;
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Categoria::getId))
                .map(this::aRespuesta)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse obtener(Long id) {
        return aRespuesta(buscar(id));
    }

    @Transactional
    public CategoriaResponse crear(CategoriaRequest req) {
        String nombre = req.nombre().trim();
        if (repository.existsByNombreIgnoreCase(nombre)) {
            throw new ConflictoException("Ya existe una categoría con el nombre '" + nombre + "'.");
        }
        Categoria c = new Categoria(nombre, TextoUtil.vacioANull(req.descripcion()), TextoUtil.vacioANull(req.imagen()));
        return aRespuesta(repository.save(c));
    }

    @Transactional
    public CategoriaResponse actualizar(Long id, CategoriaPatchRequest req) {
        Categoria c = buscar(id);
        if (req.nombre() != null) {
            String nombre = req.nombre().trim();
            repository.findByNombreIgnoreCase(nombre).filter(otra -> !otra.getId().equals(id))
                    .ifPresent(otra -> { throw new ConflictoException("Ya existe una categoría con el nombre '" + nombre + "'."); });
            c.setNombre(nombre);
        }
        if (req.descripcion() != null) c.setDescripcion(TextoUtil.vacioANull(req.descripcion()));
        if (req.imagen() != null) c.setImagen(TextoUtil.vacioANull(req.imagen()));
        return aRespuesta(repository.save(c));
    }

    @Transactional
    public void eliminar(Long id) {
        Categoria c = buscar(id);
        long cantidad = productoRepository.countByCategoriaId(id);
        if (cantidad > 0) {
            throw new ConflictoException("No se puede eliminar la categoría porque tiene " + cantidad + " producto(s) asociados.");
        }
        repository.delete(c);
    }

    public Categoria buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con id " + id + "."));
    }

    public Categoria buscarPorNombre(String nombre) {
        return repository.findByNombreIgnoreCase(nombre.trim())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: '" + nombre + "'."));
    }

    private CategoriaResponse aRespuesta(Categoria c) {
        return CategoriaResponse.desde(c, productoRepository.countByCategoriaId(c.getId()));
    }
}
