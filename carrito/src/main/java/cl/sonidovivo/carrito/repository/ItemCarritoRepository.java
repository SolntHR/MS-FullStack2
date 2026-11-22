package cl.sonidovivo.carrito.repository;

import cl.sonidovivo.carrito.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {

    List<ItemCarrito> findByIdUsuarioOrderByIdAsc(Long idUsuario);

    Optional<ItemCarrito> findByIdUsuarioAndCodigoIgnoreCase(Long idUsuario, String codigo);

    void deleteByIdUsuario(Long idUsuario);
}
