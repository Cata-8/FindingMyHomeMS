package cl.duoc.notificacionMS.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO {


    private Integer id;


    private String mensaje;


    private Date fechaEmision;


    private UsuarioDTO usuario;
    
}
