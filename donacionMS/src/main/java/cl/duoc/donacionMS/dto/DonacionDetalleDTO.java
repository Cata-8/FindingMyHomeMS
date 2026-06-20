package cl.duoc.donacionMS.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detalle basico de una donación")
public class DonacionDetalleDTO {

    @Schema(description = "ID de la donación")
    private Integer id;

    @Schema(description = "Fecha en la que fue realizada la donación")
    private Date fecha;
    
    @Schema(description = "ID de usuario que realizó la donación")
    private Integer idUsuario;
}
