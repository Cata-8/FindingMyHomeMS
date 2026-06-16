package cl.duoc.Autenticacion.model;

import java.time.LocalDateTime;

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
@Table(name = "autenticacion")
@Entity
@Schema(description = "Almacena la información de autenticación de un usuario")
public class Autenticacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de autenticación", example = "1")
    private Integer idAuth;

    @Column(nullable = false, unique = true)
    @Schema(description = "Identificador del usuario asociado", examples = {"1", "2"})
    private Integer idUsuario;

    @Schema(description = "Fecha y hora del último inicio de sesión")
    private LocalDateTime ultimoLogin;

    @Column(nullable = false)
    @Schema(description = "Estado de la autenticación", examples = {"Activo", "Inactivo"})
    private String estado;
}
