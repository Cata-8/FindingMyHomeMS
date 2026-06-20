package cl.duoc.mascotaMS.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mascota")
@Schema(description = "Representa una mascota en el sistema")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico de la mascota. Solo se permiten datos numericos", examples = {"1", "2"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la mascota entregado por el refugio", examples = {"Gaspar", "El hechicero"})
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Se clasifica de que especie es el animal, ya sea perro o gato")
    private String tipo;

    @Column(nullable = false)
    @Schema(description = "Edad de la mascota, a veces esta edad no es exacta si no una aproximación")
    private Integer edad;

    @Column(nullable = false)
    @Schema(description = "Descripcion de la personalidad del animal", examples = {"Perro jugueton y energico","Gato sociable con otros gatos y animales","Perro cariñoso y friolento, pongale watero :c"})
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Estado de la mascota, este puede ser 'Adoptado' o 'Disponible'")
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Ubicación donde se encuentra la mascota, esperando a ser adoptada")
    private String ubicacion;

    @Column(nullable = false)
    @Schema(description = "Fecha en la cual fue publicada la mascota en la pagina web")
    private Date fecha_publicacion;
}
