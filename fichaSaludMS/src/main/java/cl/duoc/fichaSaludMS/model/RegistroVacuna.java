package cl.duoc.fichaSaludMS.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registroVacuna")
public class RegistroVacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegistro;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private Integer dosis;

    @ManyToOne
    @JoinColumn(name = "ficha_salud_id")
    @JsonBackReference
    private FichaSalud fichaSalud;

    @ManyToOne
    @JoinColumn(name = "vacuna_id")
    private Vacuna vacuna;

}
