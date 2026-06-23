package cl.Duoc.HistorialAdopcionMS.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historialAdopcion")
@Schema(description = "Representa el historial de adopciones realizadas")
public class HistorialAdopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de historial de adopción", example = "1")
    private Integer idHistorial;

    @Column(nullable = false)
    @Schema(description = "Fecha de cuando fue relizada la adopción")
    private LocalDate fechaAdopcion;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    @Column(nullable = false)
    @Schema(description = "ID de usuario adoptante")
    private Integer idUsuario;

    @NotNull(message = "El ID de mascota es obligatorio")
    @Column(nullable = false)
    @Schema(description = "ID d mascota adoptada")
    private Integer idMascota;

    @PrePersist
    public void prePersist() {
        this.fechaAdopcion = LocalDate.now();
    }
}
