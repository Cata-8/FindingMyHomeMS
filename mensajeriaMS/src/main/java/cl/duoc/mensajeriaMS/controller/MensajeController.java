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

@RestController
@RequestMapping("/api/v1/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MensajeDTO dto) {

        try {

            return ResponseEntity.ok(mensajeService.crearMensaje(dto));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        return ResponseEntity.ok(mensajeService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        try {

            return ResponseEntity.ok(mensajeService.buscarPorId(id));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<?> marcarLeido(@PathVariable Integer id) {

        try {

            return ResponseEntity.ok(mensajeService.marcarComoLeido(id));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        try {

            mensajeService.eliminar(id);

            return ResponseEntity.ok("Mensaje eliminado");

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
