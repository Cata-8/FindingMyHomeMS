package cl.duoc.donacionMS.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donacion")
@Schema(description = "Representa una donación realizada por un usuario")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unica de una donación, se autoincrementa con cada nueva donacion y solo permite datos numericos", example = "1")
    private Integer id;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1000, message = "El monto mínimo de donación es $1.000")
    @Column(nullable = false)
    @Schema(description = "Monto donado, solo datos numericos sin puntos ni comas.", examples = {"25000", "10000"})
    private Integer monto;

    @Column(nullable = false)
    @Schema(description = "Fecha en la que fue realizada la donación")
    private Date fecha;

    @NotBlank(message = "El método de pago no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Método de pago utilizado para la donación", examples = {"Tarjeta", "Transferencia", "Efectivo"})
    private String metodoDePago;

    @NotNull(message = "El ID de usuario es obligatorio")
    @Size(max = 3, min = 1)
    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "ID de usuario que realizó la donación, solo se permiten datos numericos", examples = {"5", "2"})
    private Integer idUsuario;

    
}
