package cl.sonidovivo.noticias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.sonidovivo.noticias.model.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
}
