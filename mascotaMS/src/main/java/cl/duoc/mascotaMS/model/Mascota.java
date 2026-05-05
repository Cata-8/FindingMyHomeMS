package cl.duoc.mascotaMS.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private Date fecha_publicacion;
}
