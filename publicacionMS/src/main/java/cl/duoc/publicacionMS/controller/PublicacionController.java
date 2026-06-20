package cl.duoc.publicacionMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.publicacionMS.dto.PublicacionDTO;
import cl.duoc.publicacionMS.model.Publicacion;
import cl.duoc.publicacionMS.service.PublicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/publicacion")
@Tag(name = "Publicaciones", description = "Operaciones sobre publicaciones de adopción")
public class PublicacionController {

    @Autowired
    private PublicacionService service;


    @GetMapping
    @Operation(summary = "Listar publicaciones", description = "Retorna todas las publicaciones registradas en el sistema")
    public ResponseEntity<List<Publicacion>> listar() {
        List<Publicacion> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }


    @PostMapping
    @Operation(summary = "Crear publicación", description = "Registra una nueva publicación de adopción")
    public ResponseEntity<Publicacion> guardar(@RequestBody Publicacion publicacion) {
        try {
            Publicacion nueva = service.guardar(publicacion);
            return ResponseEntity.ok(nueva);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar publicación por ID", description = "Retorna una publicación según el ID proporcionado")
    public ResponseEntity<Publicacion> buscar(
            @Parameter(description = "ID de la publicación a buscar") @PathVariable Integer id) {
        try {
            Publicacion publicacion = service.buscarPorId(id);
            return ResponseEntity.ok(publicacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}/detalle")
    @Operation(summary = "Retorna un Publicacion DTO",
            description = "Retorna los datos basicos de una publicación segun su id, este metodo se utiliza para ser llamado desde otros microservicios cuando se necesiten los datos de una publicación")
    public ResponseEntity<PublicacionDTO> detalle(
            @Parameter(description = "ID de la publicación") @PathVariable Integer id) {
        try {
            Publicacion publicacion = service.buscarPorId(id);
            PublicacionDTO dto = new PublicacionDTO(
                    publicacion.getId(),
                    publicacion.getTitulo(),
                    publicacion.getFechaPublicacion(),
                    publicacion.getIdMascota()
            );
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar publicación", description = "Elimina una publicación según el ID ingresado")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la publicación a eliminar") @PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
