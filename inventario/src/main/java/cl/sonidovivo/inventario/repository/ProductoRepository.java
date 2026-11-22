package cl.sonidovivo.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.sonidovivo.inventario.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
    Optional<Producto> findByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCase(String codigo);

    long countByCategoriaId(Long idCategoria);
}
