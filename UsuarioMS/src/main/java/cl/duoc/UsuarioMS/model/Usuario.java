package cl.duoc.UsuarioMS.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name= "usuario")
@Entity
@Schema(description = "Representa informacion de un usuario registrado en el sistema")
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de usuario, solo permite datos numericos y se autoincrementa")
    private Integer idUsuario;

    @Column(nullable = false)
    @Schema(description = "Nombre de usuario")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellido de usuario")
    private String apellido;

    @Column(unique = true, nullable = false)
    @Schema(description = "Correo electrónico de usuario")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Contraseña privada de usuario")
    private String password;

    @Schema(description = "Teléfono de usuario, este campo no es necesario, puede ser nulo")
    private String telefono;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación del usuario")
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Adoptante adoptante;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Refugio refugio;



}
