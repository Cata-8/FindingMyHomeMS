package cl.duoc.donacionMS.model;

import java.util.Date;

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
@Table(name = "donacion")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer monto;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private String metodoDePago;

    @Column(name = "refugio_id", nullable = false)
    private Integer idUsuarioRefugio;

    @Column(name = "adoptante_id", nullable = false)
    private Integer idUsuarioAdoptante;

    
}
