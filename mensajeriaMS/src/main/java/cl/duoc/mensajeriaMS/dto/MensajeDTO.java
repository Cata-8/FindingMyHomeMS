package cl.duoc.mensajeriaMS.dto;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDTO {

    private Integer id;

    private Integer idRemitente;
    
    private String contenido;

    private Integer idDestinatario;

    private LocalDate fechaEnvio;
    
    private boolean leido;
}
