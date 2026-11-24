package cl.sonidovivo.noticias.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "noticia")
@Getter
@Setter
@NoArgsConstructor
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String categoria;

    @Column(nullable = false, length = 180)
    private String titulo;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, length = 500)
    private String resumen;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String contenido;

    @Column(length = 500)
    private String imagen;

    public Noticia(String categoria, String titulo, LocalDate fecha, String resumen, String contenido, String imagen) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.fecha = fecha;
        this.resumen = resumen;
        this.contenido = contenido;
        this.imagen = imagen;
    }
}
