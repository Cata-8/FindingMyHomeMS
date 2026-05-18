package cl.Duoc.HistorialAdopcionMS.model;

import java.time.LocalDate;

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

    private LocalDate fechaAdopcion;

    private Integer idUsuario;

    private Integer idMascota;

    @PrePersist
    public void prePersist() {
        this.fechaAdopcion = LocalDate.now();
    }

}
