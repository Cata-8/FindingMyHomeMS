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
    private Integer id_usuario;

    @Column(nullable = false)
    private String nombre_Refugio;

    @Column(nullable = false)
    private String dirección;

    @Column(nullable = false)
    private String descripción;

    @Column(nullable = false)
    private String telefono_Contacto;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
