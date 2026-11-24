package cl.sonidovivo.noticias.dto;

import java.time.LocalDate;

import cl.sonidovivo.noticias.model.Noticia;

public record NoticiaResponse(Long id, String categoria, String titulo, LocalDate fecha,
        String resumen, String contenido, String imagen) {
    public static NoticiaResponse desde(Noticia noticia) {
        return new NoticiaResponse(noticia.getId(), noticia.getCategoria(), noticia.getTitulo(),
                noticia.getFecha(), noticia.getResumen(), noticia.getContenido(), noticia.getImagen());
    }
}
