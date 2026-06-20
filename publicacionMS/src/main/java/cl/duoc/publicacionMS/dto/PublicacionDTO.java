package cl.duoc.publicacionMS.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos basicos de una publicación de adopción")
public class PublicacionDTO {

    @Schema(description = "ID de publicación")
    private Integer id;

    @Schema(description = "Título de la publicación")
    private String titulo;

    @Schema(description = "Fecha en la que se realizó la publicación")
    private Date fechaPublicacion;

    @Schema(description = "ID de mascota asociada a la publicación")
    private Integer idMascota;
}
