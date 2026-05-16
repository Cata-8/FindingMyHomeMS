package cl.duoc.donacionMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptanteDTO {

    private Integer idUsuario;
    private String nombre;
    private String apellido;
}
