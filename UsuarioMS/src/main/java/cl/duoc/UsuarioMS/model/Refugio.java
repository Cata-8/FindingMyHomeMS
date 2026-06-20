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

    @Column(nullable = false)
    @Schema(description = "Nombre del refugio")
    private String nombreRefugio;

    @Column(nullable = false)
    @Schema(description = "Dirección del refugio")
    private String direccion;

    @Column(nullable = false)
    @Schema(description = "Descripción del refugio")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Teléfono de contacto de refugio")
    private String telefonoContacto;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;
}
