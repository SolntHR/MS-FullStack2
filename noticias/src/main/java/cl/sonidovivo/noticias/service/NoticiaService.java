package cl.sonidovivo.noticias.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.sonidovivo.noticias.dto.NoticiaPatchRequest;
import cl.sonidovivo.noticias.dto.NoticiaRequest;
import cl.sonidovivo.noticias.dto.NoticiaResponse;
import cl.sonidovivo.noticias.exception.RecursoNoEncontradoException;
import cl.sonidovivo.noticias.model.Noticia;
import cl.sonidovivo.noticias.repository.NoticiaRepository;

@Service
public class NoticiaService {
    private final NoticiaRepository repository;

    public NoticiaService(NoticiaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<NoticiaResponse> listar() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Noticia::getFecha).reversed())
                .map(NoticiaResponse::desde)
                .toList();
    }

    @Transactional(readOnly = true)
    public NoticiaResponse obtener(Long id) {
        return NoticiaResponse.desde(buscar(id));
    }

    @Transactional
    public NoticiaResponse crear(NoticiaRequest req) {
        Noticia noticia = new Noticia(req.categoria().trim(), req.titulo().trim(), req.fecha(),
                req.resumen().trim(), req.contenido().trim(), limpiar(req.imagen()));
        return NoticiaResponse.desde(repository.save(noticia));
    }

    /** PATCH: solo cambia los campos que vienen en la petición (los null se ignoran) */
    @Transactional
    public NoticiaResponse actualizar(Long id, NoticiaPatchRequest req) {
        Noticia noticia = buscar(id);
        if (req.categoria() != null) noticia.setCategoria(req.categoria().trim());
        if (req.titulo() != null) noticia.setTitulo(req.titulo().trim());
        if (req.fecha() != null) noticia.setFecha(req.fecha());
        if (req.resumen() != null) noticia.setResumen(req.resumen().trim());
        if (req.contenido() != null) noticia.setContenido(req.contenido().trim());
        if (req.imagen() != null) noticia.setImagen(limpiar(req.imagen())); // "" quita la imagen
        return NoticiaResponse.desde(repository.save(noticia));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private Noticia buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Noticia no encontrada con id " + id + "."));
    }

    private String limpiar(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }
}
