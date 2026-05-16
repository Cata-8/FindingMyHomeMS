package cl.duoc.donacionMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefugioDTO {

    private Integer idUsuario;

    private String nombreRefugio;

    private String telefonoContacto;
}
