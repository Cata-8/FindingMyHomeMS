package cl.duoc.fichaSaludMS.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detalle completo de una ficha médica de una mascota, incluyendo sus vacunas")
public class FichaSaludDTO {

    @Schema(description = "ID de la ficha de salud")
    private Integer id;

    @Schema(description = "Enfermedades que tenga la mascota", examples = {"Ninguna", "Cáncer"})
    private String enfermedades;

    @Schema(description = "Estado reproductivo de la mascota", example = "Esterilizado")
    private String estadoReproductivo;

    @Schema(description = "Indica si el animal posee microchip")
    private boolean microship;

    @Schema(description = "Indica si el animal está desparasitado")
    private boolean desparasitado;

    @Schema(description = "ID de la mascota asociada a la ficha")
    private Integer idMascota;

    @Schema(description = "Listado de vacunas registradas en la ficha de salud")
    private List<RegistroVacunaDTO> vacunas;
}
