package cl.duoc.mensajeriaMS.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos de un mensaje enviado entre usuarios")
public class MensajeDTO {

    @Schema(description = "ID del mensaje")
    private Integer id;

    @Schema(description = "ID del remitente")
    private Integer idRemitente;
    
    @Schema(description = "Contenido del mensaje")
    private String contenido;

    @Schema(description = "ID del destinatario")
    private Integer idDestinatario;

    @Schema(description = "Fecha de envío del mensaje")
    private LocalDate fechaEnvio;
    
    @Schema(description = "Indica si el mensaje fue leído")
    private boolean leido;
}
