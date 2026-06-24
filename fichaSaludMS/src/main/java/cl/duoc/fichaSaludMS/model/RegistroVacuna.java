package cl.duoc.fichaSaludMS.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registroVacuna")
@Schema(description = "Representa registro de aplicación de vacuna(s)")
public class RegistroVacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de registro, solo permite datos numericos y se autoincrementa en cada registro", example = "1")
    private Integer idRegistro;

    @NotNull(message = "La fecha de aplicación es obligatoria")
    @Column(nullable = false)
    @Schema(description = "Fecha de aplicación de vacuna(s)")
    private Date fecha;

    @NotNull(message = "La dosis es obligatoria")
    @Min(value = 1, message = "La dosis debe ser al menos 1")
    @Column(nullable = false)
    @Schema(description = "Cantida de dosis en gr", example = "2")
    private Integer dosis;

    @NotNull(message = "La ficha de salud asociada es obligatoria")
    @ManyToOne
    @JoinColumn(name = "ficha_salud_id")
    @JsonBackReference
    private FichaSalud fichaSalud;

    @NotNull(message = "La vacuna asociada es obligatoria")
    @ManyToOne
    @JoinColumn(name = "vacuna_id")
    private Vacuna vacuna;

}
