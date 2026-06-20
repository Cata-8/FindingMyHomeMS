package Duoc.cl.SolicitudMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Duoc.cl.SolicitudMS.dto.SolicitudDTO;
import Duoc.cl.SolicitudMS.model.Solicitud;
import Duoc.cl.SolicitudMS.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/solicitudes")
@Tag(name = "Solicitudes", description = "Operaciones sobre solicitudes de adopción")
public class SolicitudController {

    @Autowired
    private SolicitudService soliService;


    @PostMapping
    @Operation(summary = "Crear solicitud", description = "Registra una nueva solicitud de adopción")
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudDTO dto) {
        try {
            Solicitud nueva = soliService.crearSolicitud(dto);
            return ResponseEntity.ok(nueva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    @Operation(summary = "Listar solicitudes", description = "Retorna todas las solicitudes registradas en el sistema")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(soliService.listar());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar solicitud por ID", description = "Retorna una solicitud según el ID proporcionado")
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID de la solicitud a buscar") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(soliService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de solicitud", description = "Actualiza el estado de una solicitud, por ejemplo: Aceptada, En proceso, Denegada")
    public ResponseEntity<?> cambiarEstado(
            @Parameter(description = "ID de la solicitud") @PathVariable Integer id,
            @Parameter(description = "Nuevo estado de la solicitud") @RequestParam String estado) {
        try {
            return ResponseEntity.ok(soliService.cambiarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar solicitud", description = "Elimina una solicitud según el ID ingresado")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la solicitud a eliminar") @PathVariable Integer id) {
        try {
            soliService.eliminar(id);
            return ResponseEntity.ok("Solicitud eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
