package cl.duoc.UsuarioMS.model;

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
public class Refugio {

    @Id
    private Integer idUsuario;

    @Column(nullable = false)
    private String nombreRefugio;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String telefonoContacto;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
