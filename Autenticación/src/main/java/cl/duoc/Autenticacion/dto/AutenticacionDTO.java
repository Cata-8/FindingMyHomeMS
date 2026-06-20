package cl.duoc.Autenticacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos necesarios para registrar o iniciar sesión de un usuario")
public class AutenticacionDTO {

    @Schema(description = "ID del usuario asociado")
    private Integer idUsuario;

}
