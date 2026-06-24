package cl.duoc.UsuarioMS.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "adoptante")
@Entity
@Schema(description = "Información de un usuario adoptante")
public class Adoptante {

    @Id
    @Schema(description = "Identificador único del usuario")
    private Integer idUsuario;

    @NotNull(message = "El usuario asociado es obligatorio")
    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

}
