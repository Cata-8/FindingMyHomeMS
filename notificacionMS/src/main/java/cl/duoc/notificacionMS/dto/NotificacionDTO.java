package cl.duoc.notificacionMS.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos basicos de una notificación")
public class NotificacionDTO {

    @Schema(description = "ID de la notificación")
    private Integer id;

    @Schema(description = "Mensaje que contiene la notificación")
    private String mensaje;

    @Schema(description = "Fecha de emisión de la notificación")
    private Date fechaEmision;

    @Schema(description = "ID de usuario destinatario")
    private Integer idUsuario;
    
}
