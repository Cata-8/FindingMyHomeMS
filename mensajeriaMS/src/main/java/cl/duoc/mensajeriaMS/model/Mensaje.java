package cl.duoc.mensajeriaMS.model;

import java.time.LocalDate;

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
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer idRemitente;

    @Column(nullable = false)
    private Integer idDestinatario;

    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private LocalDate fechaEnvio;

    @PrePersist
    public void prePersist() {
        this.fechaEnvio = LocalDate.now();
    }

    @Column(nullable = false)
    private boolean leido;
}
