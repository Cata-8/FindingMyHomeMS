package cl.duoc.UsuarioMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos de un usuario de tipo refugio")
public class RefugioDTO {

    @Schema(description = "Nombre del responsable del refugio")
    private String nombre;

    @Schema(description = "Apellido del responsable del refugio")
    private String apellido;

    @Schema(description = "Correo electronico del refugio")
    private String email;

    @Schema(description = "Teléfono del responsable del refugio, este campo no es necesario, puede ser nulo")
    private String telefono;

    @Schema(description = "Contraseña del refugio")
    private String password; 

    @Schema(description = "Nombre del refugio")
    private String nombreRefugio;

    @Schema(description = "Dirección del refugio")
    private String direccion;

    @Schema(description = "Descripción del refugio")
    private String descripcion;

    @Schema(description = "Teléfono de contacto del refugio")
    private String telefonoContacto;
}
