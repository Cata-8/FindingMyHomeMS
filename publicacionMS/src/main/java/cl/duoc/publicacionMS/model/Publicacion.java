package cl.duoc.publicacionMS.model;

import java.util.Date;

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
@Entity
@Table(name = "publicacion")
@Schema(description = "Representa una publicación de adopción hecha por un usuario refugio")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de publicación, solo permite datos numericos", examples = {"1","2"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Título de la publicación")
    private String titulo;

    @Column(nullable = false)
    @Schema(description = "Descripción de la publicación")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Fecha en la que se realizó la publicación")
    private Date fechaPublicacion;

    @Column(nullable = false)
    @Schema(description = "Estado en el que se encuentra la publicación", examples = {"Activa", "Pausada", "Eliminada"})
    private String estado;

    @Column(name = "id_mascota",nullable = false)
    @Schema(description = "ID de mascota asociada a la publicación")
    private Integer idMascota;
}
