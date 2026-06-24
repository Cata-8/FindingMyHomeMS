package cl.duoc.mascotaMS.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
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
@Table(name = "mascota")
@Schema(description = "Representa informacion de una mascota registrado en el sistema")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico de la mascota. Solo se permiten datos numericos", examples = {"1", "2"})
    private Integer id;

    @NotBlank(message = "El nombre de la mascota no puede estar vacío")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    @Column(nullable = false)
    @Schema(description = "Nombre de la mascota entregado por el refugio", examples = {"Gaspar", "El hechicero"})
    private String nombre;

    @NotBlank(message = "El tipo de mascota no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Se clasifica de que especie es el animal, ya sea perro o gato")
    private String tipo;

    @NotNull(message = "La edad de la mascota es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 30, message = "La edad no puede ser mayor a 30 años")
    @Column(nullable = false)
    @Schema(description = "Edad de la mascota, a veces esta edad no es exacta si no una aproximación")
    private Integer edad;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(nullable = false)
    @Schema(description = "Descripcion de la personalidad del animal", examples = {"Perro jugueton y energico","Gato sociable con otros gatos y animales","Perro cariñoso y friolento, pongale watero :c"})
    private String descripcion;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Estado de la mascota, este puede ser 'Adoptado' o 'Disponible'")
    private String estado;

    @NotBlank(message = "La ubicación no puede estar vacía")
    @Size(min = 2, max = 100, message = "La ubicación debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    @Schema(description = "Ubicación donde se encuentra la mascota, esperando a ser adoptada")
    private String ubicacion;

    @Column(nullable = false)
    @Schema(description = "Fecha en la cual fue publicada la mascota en la pagina web")
    private Date fecha_publicacion;
}
