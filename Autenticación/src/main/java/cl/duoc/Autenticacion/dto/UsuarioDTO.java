package cl.duoc.Autenticacion.dto;

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

    @Schema(description = "Nombre del usuario")
    private String nombre;

    @Schema(description = "Apellido del usuario")
    private String apellido;

}
