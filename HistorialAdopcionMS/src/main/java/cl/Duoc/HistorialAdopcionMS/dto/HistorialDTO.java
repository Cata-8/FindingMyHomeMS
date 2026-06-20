package cl.Duoc.HistorialAdopcionMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos necesarios para registrar un historial de adopción")
public class HistorialDTO {

    @Schema(description = "ID de usuario adoptante")
    private Integer idUsuario;

    @Schema(description = "ID de mascota")
    private Integer idMascota;
}
