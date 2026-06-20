package cl.duoc.fichaSaludMS.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos necesarios para registrar la aplicación de una vacuna")
public class RegistroVacunaDTO {

    @Schema(description = "ID de la vacuna aplicada")
    private Integer idVacuna;

    @Schema(description = "Fecha de aplicación de la vacuna")
    private Date fecha;

    @Schema(description = "Cantidad de dosis en gr", example = "2")
    private Integer dosis;
}
