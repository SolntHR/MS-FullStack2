package cl.sonidovivo.carrito.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_carrito")
@Getter
@Setter
@NoArgsConstructor
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_producto", nullable = false)
    private Long idProducto;

    @Column(nullable = false, length = 10)
    private String codigo;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(length = 60)
    private String marca;

    @Column(length = 60)
    private String modelo;

    @Column(nullable = false)
    private Integer precio;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagen;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_agregado", nullable = false)
    private LocalDateTime fechaAgregado;

    @PrePersist
    void antesDeGuardar() {
        if (fechaAgregado == null) {
            fechaAgregado = LocalDateTime.now();
        }
    }
}
