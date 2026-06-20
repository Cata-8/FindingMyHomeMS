package cl.duoc.donacionMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos basicos de un usuario, obtenidos desde el microservicio de Usuario")
public class UsuarioDTO {

    @Schema(description = "Identificador único del usuario")
    private Integer idUsuario;
}
