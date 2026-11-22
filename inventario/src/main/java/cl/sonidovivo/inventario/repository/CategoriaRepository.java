package cl.sonidovivo.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.sonidovivo.inventario.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    Optional<Categoria> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);
    
}
