package cl.duoc.Autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacionDTO {

    private Integer idUsuario;
    
    private String password;

}
