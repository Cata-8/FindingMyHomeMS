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
import Duoc.cl.SolicitudMS.service.SolicitudService;

@RestController
@RequestMapping("/api/v1/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService soliService;

    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudDTO dto) {
        try {
            return ResponseEntity.ok("Solicitud creada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear solicitud: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(soliService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
    try{
        return ResponseEntity.ok(soliService.buscarPorId(id));
    }catch (Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id, @RequestParam String estado){
    try{
        return ResponseEntity.ok(soliService.cambiarEstado(id, estado));
    }catch (RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
    try{
        soliService.eliminar(id);
        return ResponseEntity.ok("Solicitud eliminada correctamente");
    }catch (RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
