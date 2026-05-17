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

@RestController
@RequestMapping("/api/v1/publicacion")
public class PublicacionController {

    @Autowired
    private PublicacionService service;

    @GetMapping
    public ResponseEntity<List<Publicacion>> listar() {
        List<Publicacion> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<Publicacion> guardar(@RequestBody Publicacion publicacion) {
        try {
            Publicacion nueva = service.guardar(publicacion);
            return ResponseEntity.ok(nueva);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> buscar(@PathVariable Integer id) {
        try {
            Publicacion publicacion = service.buscarPorId(id);
            return ResponseEntity.ok(publicacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<PublicacionDTO> detalle(@PathVariable Integer id){
        try {
            PublicacionDTO dto = service.obtenerDetalle(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
