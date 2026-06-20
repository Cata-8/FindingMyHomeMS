package cl.duoc.mascotaMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos de una mascota")
public class MascotaDTO {

    @Schema(description = "ID unico de la mascota. Solo se permiten datos numericos", examples = {"1", "2"})
    private Integer id;

    @Schema(description = "Nombre de la mascota")
    private String nombre;

    @Schema(description = "Se clasifica de que especie es el animal, ya sea perro o gato")
    private String tipo;

    @Schema(description = "Nombre de la mascota entregado por el refugio", examples = {"Gaspar", "El hechicero"})
    private String estado;
}
