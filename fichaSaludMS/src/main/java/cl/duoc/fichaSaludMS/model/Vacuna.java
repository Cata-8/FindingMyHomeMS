package cl.duoc.fichaSaludMS.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vacuna")
@Schema(description = "Registro de vacunas que posee la mascotas")
public class Vacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la vacuna, solo permite datos numericos y se autoincrementa", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la vacuna", example = "Antirrábica")
    private String nombre;

    @OneToMany(mappedBy = "vacuna")
    @JsonBackReference
    private List<RegistroVacuna> registroVacunas;
    
}
