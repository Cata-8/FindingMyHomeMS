package cl.duoc.fichaSaludMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos basicos de una mascota, obtenidos desde el microservicio de Mascota")
public class MascotaDTO {

    @Schema(description = "Identificador único de la mascota")
    private Integer id;

    @Schema(description = "Nombre de la mascota")
    private String nombre;

    @Schema(description = "Se clasifica de que especie es el animal, ya sea perro o gato")
    private String tipo;
}
