package cl.Duoc.HistorialAdopcionMS.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historialAdopcion")
public class HistorialModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorial;

    @Column(nullable = false)
    private LocalDate fechaAdopcion;

    @Column(nullable = false)
    private Integer idUsuario;

    @Column(nullable = false)
    private Integer idMascota;

    @PrePersist
    public void prePersist() {
        this.fechaAdopcion = LocalDate.now();
    }

}
