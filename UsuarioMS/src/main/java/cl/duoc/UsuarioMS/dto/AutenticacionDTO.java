package cl.duoc.UsuarioMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos utilizados para la autenticación de un usuario")
public class AutenticacionDTO {

    @Schema(description = "Identificador único del usuario")
    private Integer idUsuario;
}
