package cl.duoc.Autenticacion.model;

import java.time.LocalDateTime;

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
@Table(name = "atenticacion")
@Entity
public class Autenticacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idAuth;

    @Column(nullable = false, unique = true)
    private String idUsuario;

    @Column(nullable = false)
    private String passwordHash;

    private LocalDateTime ultimoLogin;

    @Column(nullable = false)
    private String estado;
}
