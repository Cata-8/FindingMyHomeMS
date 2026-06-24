package cl.duoc.Autenticacion.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "autenticacion")
@Entity
@Schema(description = "Almacena la información de autenticación de un usuario")
public class Autenticacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de autenticación, Se autoincrementa y solo permite datos numericos", example = "1")
    private Integer idAuth;

    @Column(nullable = false, unique = true)
    @NotNull(message = "El ID de usuario es obligatorio")
    @Size(max = 3, min = 1)
    @Schema(description = "Identificador del usuario asociado", examples = {"1", "2"})
    private Integer idUsuario;

    @Schema(description = "Fecha y hora del último inicio de sesión")
    private LocalDateTime ultimoLogin;

    @Column(nullable = false)
    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 8, min = 3)
    @Schema(description = "Estado de la autenticación", examples = {"Activo", "Inactivo"})
    private String estado;
}
