package cl.duoc.mensajeriaMS.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mensajeria")
@Schema(description = "Representa mensajes enviados entre usuarios")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del mensaje, se autoincrementa y solo permite datos numericos", examples = {"1","20"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "ID del remitente")
    private Integer idRemitente;

    @Column(nullable = false)
    @Schema(description = "ID del destinatario")
    private Integer idDestinatario;

    @Column(nullable = false)
    @Schema(description = "Contenido del mensaje")
    private String contenido;

    @Column(nullable = false)
    @Schema(description = "Fecha de envío del mensaje")
    private LocalDate fechaEnvio;

    @PrePersist
    public void prePersist() {
        this.fechaEnvio = LocalDate.now();
    }

    @Column(nullable = false)
    @Schema(description = "Indica si el mensaje fue leído")
    private boolean leido;
}
