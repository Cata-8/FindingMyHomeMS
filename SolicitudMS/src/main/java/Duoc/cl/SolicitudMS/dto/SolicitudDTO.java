package Duoc.cl.SolicitudMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos necesarios para crear una solicitud de adopción")
public class SolicitudDTO {

    @Schema(description = "Mensaje del solicitante hacia el refugio indicando porque quiere realizar la solicitud de adopción", example = "quiero adoptar a Rocky porque está bonito")
    private String mensaje;

    @Schema(description = "ID de mascota relacionada a la solicitud")
    private Integer idMascota;

    @Schema(description = "ID de usuario adoptante que hace la solicitud")
    private Integer idUsuarioAdoptante;
}
