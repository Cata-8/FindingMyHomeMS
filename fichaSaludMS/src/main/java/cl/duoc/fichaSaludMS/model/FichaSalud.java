package cl.duoc.fichaSaludMS.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "fichaSalud")
@Schema(description = "Representa una ficha médica de una mascota")
public class FichaSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la ficha, permite solo datos numericos y se autoincrementa", example = "1")
    private Integer id;

    @NotBlank(message = "El campo enfermedades no puede estar vacío (use 'Ninguna' si no hay enfermedades)")
    @Size(max = 300, message = "La descripción de enfermedades no puede superar los 300 caracteres")
    @Column(nullable = false)
    @Schema(description = "Enfermedades que tenga la mascota", examples = {"Ninguna", "Cáncer"})
    private String enfermedades;

    @NotBlank(message = "El estado reproductivo no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Muestra estado reproductivo de la mascota", example = "Esterilizado")
    private String estadoReproductivo;

    @Column(nullable = false)
    @Schema(description = "Indica si el animal posee microchip", examples = {"true", "false"})
    private boolean microship;

    @Column(nullable = false)
    @Schema(description = "Indica si el animal está desparasitado", examples = {"true", "false"})
    private boolean desparasitado;

    @NotNull(message = "El ID de mascota es obligatorio")
    @Size(max = 3, min = 1)
    @Column(name = "id_mascota", nullable = false)
    @Schema(description = "ID que representa la mascota asociada", example = "1")
    private Integer idMascota;

    @OneToMany(mappedBy = "fichaSalud", cascade = CascadeType.ALL)
    @JsonBackReference
    @Schema(description = "Listado de vacunas registradas")
    private List<RegistroVacuna> registroVacunas;
    
}
