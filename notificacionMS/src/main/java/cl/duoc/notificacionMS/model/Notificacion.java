package cl.duoc.notificacionMS.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "notificacion")
@Schema(description = "Representa notificaciones enviadas a los usuarios")
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la notificacion, solo permite datos numericos y se autoincrementa")
    private Integer id;

    
    @Column(nullable = false)
    @Schema(description = "Mensaje que contiene la notificación")
    private String mensaje;


    @Column(nullable = false)
    @Schema(description = "Fecha de emisión de la notificación")
    private Date fechaEmision;


    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "ID de usuario destinatario")
    private Integer usuarioId;

}
