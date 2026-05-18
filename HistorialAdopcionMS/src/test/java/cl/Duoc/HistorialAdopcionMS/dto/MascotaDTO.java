package cl.Duoc.HistorialAdopcionMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MascotaDTO {

    private Integer id;
    private String nombre;
    private String tipo;
    private String estado;

}
