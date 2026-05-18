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

import cl.Duoc.HistorialAdopcionMS.dto.HistorialDTO;
import cl.Duoc.HistorialAdopcionMS.model.HistorialAdopcion;
import cl.Duoc.HistorialAdopcionMS.service.HistorialService;

@RestController
@RequestMapping("/api/v1/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody HistorialDTO dto) {

        try {

            HistorialAdopcion nuevo = historialService.crearHistorial(dto);

            return ResponseEntity.ok(nuevo);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        return ResponseEntity.ok(historialService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        try {

            return ResponseEntity.ok(historialService.buscarPorId(id));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        try {

            historialService.eliminar(id);

            return ResponseEntity.ok("Historial eliminado");

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
