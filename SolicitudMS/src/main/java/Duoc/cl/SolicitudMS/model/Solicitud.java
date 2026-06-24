package Duoc.cl.SolicitudMS.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "solicitud")
@Schema(description = "Representa una solicitud de adopción")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico de solicitud, solo permite datos numericos, se autoincrementa", examples = {"1","2"})
    private Integer idSolicitud;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(min = 10, max = 500, message = "El mensaje debe tener entre 10 y 500 caracteres")
    @Column(nullable = false)
    @Schema(description = "Mensaje del solicitante hacia el refugio indicando porque quiere realizar la solicitud de adopción", example = "quiero adoptar a Rocky porque está bonito")
    private String mensaje;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Estado de la solicitud", examples = {"Aceptada", "En proceso", "Denegada"})
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Fecha en la cual fue realizada la solicitud")
    private LocalDateTime fechaSolicitud;

    @PrePersist
    public void prePersist() {
        this.fechaSolicitud = LocalDateTime.now();
    }

    @NotNull(message = "El ID de mascota es obligatorio")
    @Column(nullable = false)
    @Schema(description = "ID de mascota relacionada a la solicitud")
    private Integer idMascota;

    @NotNull(message = "El ID de usuario adoptante es obligatorio")
    @Column(nullable = false)
    @Schema(description = "ID de usuario adoptante que hace la solicitud")
    private Integer idUsuarioAdoptante;

}
