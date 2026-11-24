package cl.sonidovivo.contacto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.sonidovivo.contacto.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}
