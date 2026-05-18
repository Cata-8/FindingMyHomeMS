package cl.duoc.publicacionMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDTO {

    private Integer id;
    private String nombre;
    private String tipo;
}
