package cl.duoc.fichaSaludMS.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
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
@Table(name = "fichaSalud")
public class FichaSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String enfermedades;

    @Column(nullable = false)
    private String estadoReproductivo;

    @Column(nullable = false)
    private boolean microship;

    @Column(nullable = false)
    private boolean desparasitado;

    @Column(name = "id_mascota", nullable = false)
    private Integer idMascota;

    @OneToMany(mappedBy = "fichaSalud", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RegistroVacuna> registroVacunas;
    
}
