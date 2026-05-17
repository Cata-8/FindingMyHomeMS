package Duoc.cl.SolicitudMS.model;

import java.time.LocalDateTime;

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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSolicitud;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    @PrePersist
    public void prePersist() {
        this.fechaSolicitud = LocalDateTime.now();
    }

    @Column(nullable = false)
    private Integer idMascota;

    @Column(nullable = false)
    private Integer idUsuarioAdoptante;

}
