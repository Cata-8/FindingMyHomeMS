package cl.duoc.notificacionMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.notificacionMS.dto.NotificacionDTO;
import cl.duoc.notificacionMS.model.Notificacion;
import cl.duoc.notificacionMS.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificaciones", description = "Operaciones sobre notificaciones de usuarios")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    @Operation(summary = "Listar notificaciones", description = "Retorna todas las notificaciones registradas en el sistema")
    public ResponseEntity<List<Notificacion>> listar() {
        List<Notificacion> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar notificación por ID", description = "Retorna una notificación según el ID proporcionado")
    public ResponseEntity<Notificacion> buscar(
            @Parameter(description = "ID de la notificación a buscar") @PathVariable Integer id) {
        try {
            Notificacion notificacion = service.buscarPorId(id);
            return ResponseEntity.ok(notificacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/notificacionDetallada")
    @Operation(summary = "Retorna una Notificacion DTO",
            description = "Retorna los datos detallados de una notificación segun su id, este metodo se utiliza para ser llamado desde otros microservicios cuando se necesiten los datos de una notificación")
    public ResponseEntity<NotificacionDTO> detalle(
            @Parameter(description = "ID de la notificación") @PathVariable Integer id) {
        try {
            Notificacion notificacion = service.buscarPorId(id);
            NotificacionDTO dto = new NotificacionDTO(
                    notificacion.getId(),
                    notificacion.getMensaje(),
                    notificacion.getFechaEmision(),
                    notificacion.getUsuarioId()
            );
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación para un usuario")
    public ResponseEntity<Notificacion> guardar(@RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = service.guardar(notificacion);
        return ResponseEntity.ok(nuevaNotificacion);
    }

}
