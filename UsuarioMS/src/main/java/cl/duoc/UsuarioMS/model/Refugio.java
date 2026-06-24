package cl.duoc.UsuarioMS.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "refugio")
@Entity
@Schema(description = "Representa información de usuario refugio")
public class Refugio {

    @Id
    @Schema(description = "Identificador único del usuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre del refugio no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del refugio debe tener entre 3 y 100 caracteres")
    @Column(nullable = false)
    @Schema(description = "Nombre del refugio")
    private String nombreRefugio;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    @Column(nullable = false)
    @Schema(description = "Dirección del refugio")
    private String direccion;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    @Column(nullable = false)
    @Schema(description = "Descripción del refugio")
    private String descripcion;

    @NotBlank(message = "El teléfono de contacto no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Teléfono de contacto de refugio")
    private String telefonoContacto;

    @NotNull(message = "El usuario asociado es obligatorio")
    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;
}
