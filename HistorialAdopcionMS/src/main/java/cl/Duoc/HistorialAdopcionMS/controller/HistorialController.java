package cl.Duoc.HistorialAdopcionMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.Duoc.HistorialAdopcionMS.model.HistorialAdopcion;
import cl.Duoc.HistorialAdopcionMS.dto.HistorialDTO;
import cl.Duoc.HistorialAdopcionMS.service.HistorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/historial")
@Tag(name = "Historial de Adopción", description = "Operaciones sobre el historial de adopciones realizadas")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @PostMapping
    @Operation(summary = "Crear historial", description = "Registra un nuevo historial de adopción")
    public ResponseEntity<?> crear(@RequestBody HistorialDTO dto) {
        try {
            HistorialAdopcion nuevo = historialService.crearHistorial(dto);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Listar historiales", description = "Retorna todos los historiales de adopción registrados")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(historialService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar historial por ID", description = "Retorna un historial de adopción según el ID proporcionado")
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID del historial a buscar") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(historialService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar historial", description = "Elimina un historial de adopción según el ID ingresado")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del historial a eliminar") @PathVariable Integer id) {
        try {
            historialService.eliminar(id);
            return ResponseEntity.ok("Historial eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
