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

    @Schema(description = "Identificador único de la mascota")
    private Integer id;

    @Schema(description = "Nombre de la mascota")
    private String nombre;

    @Schema(description = "Tipo de mascota: perro, gato")
    private String tipo;

    @Schema(description = "Estado actual de la mascota: disponible, en adopción, adoptado")
    private String estado;
}
