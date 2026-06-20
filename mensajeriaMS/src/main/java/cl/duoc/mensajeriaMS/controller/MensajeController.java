package cl.duoc.mensajeriaMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.mensajeriaMS.dto.MensajeDTO;
import cl.duoc.mensajeriaMS.service.MensajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/mensajes")
@Tag(name = "Mensajes", description = "Operaciones sobre mensajes entre usuarios")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @PostMapping
    @Operation(summary = "Crear mensaje", description = "Registra un nuevo mensaje enviado entre usuarios")
    public ResponseEntity<?> crear(@RequestBody MensajeDTO dto) {
        try {
            return ResponseEntity.ok(mensajeService.crearMensaje(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Listar mensajes", description = "Retorna todos los mensajes registrados en el sistema")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(mensajeService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mensaje por ID", description = "Retorna un mensaje según el ID proporcionado")
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID del mensaje a buscar") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(mensajeService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/leer")
    @Operation(summary = "Marcar mensaje como leído", description = "Actualiza el estado de un mensaje a leído según su ID")
    public ResponseEntity<?> marcarLeido(
            @Parameter(description = "ID del mensaje a marcar como leído") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(mensajeService.marcarComoLeido(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mensaje", description = "Elimina un mensaje según el ID ingresado")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del mensaje a eliminar") @PathVariable Integer id) {
        try {
            mensajeService.eliminar(id);
            return ResponseEntity.ok("Mensaje eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
