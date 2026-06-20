package cl.duoc.fichaSaludMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos basicos de una vacuna")
public class VacunaDTO {

    @Schema(description = "ID de la vacuna")
    private Integer id;

    @Schema(description = "Nombre de la vacuna", example = "Antirrábica")
    private String nombre;
}
