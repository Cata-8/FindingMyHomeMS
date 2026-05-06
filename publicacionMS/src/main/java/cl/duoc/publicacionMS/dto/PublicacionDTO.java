package cl.duoc.publicacionMS.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {

    private Integer id;
    private String titulo;
    private Date fechaPublicacion;

    private MascotaDTO mascota;

}
