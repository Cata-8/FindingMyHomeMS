package Duoc.cl.SolicitudMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDTO {

    private String mensaje;

    private Integer idMascota;

    private Integer idUsuarioAdoptante;
}
