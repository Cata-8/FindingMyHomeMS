package cl.duoc.UsuarioMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos de un usuario de tipo adoptante")
public class AdoptanteDTO {

    @Schema(description = "Nombre del adoptante")
    private String nombre;

    @Schema(description = "Apellido del adoptante")
    private String apellido;

    @Schema(description = "Correo electrónico del adoptante")
    private String email;

    @Schema(description = "Teléfono del adoptante, este campo no es necesario, puede ser nulo")
    private String telefono;

    @Schema(description = "Contraseña del adoptante")
    private String password;
}
