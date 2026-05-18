package cl.duoc.UsuarioMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefugioDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String password; 

    private String nombreRefugio;
    private String direccion;
    private String descripcion;
    private String telefonoContacto;
}
