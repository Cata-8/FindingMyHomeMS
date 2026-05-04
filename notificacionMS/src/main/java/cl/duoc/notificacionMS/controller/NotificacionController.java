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

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    public ResponseEntity<List<Notificacion>> listar(){
        List<Notificacion> lista = service.listar();
        
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscar(@PathVariable Integer id){
        try {
            Notificacion notificacion = service.buscarPorId(id);
            return ResponseEntity.ok(notificacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/notificacionDetallada")
    public ResponseEntity<NotificacionDTO>detalle(@PathVariable Integer id){
        try {
            NotificacionDTO dto = service.obtenerNotificacion(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Notificacion> guardar(@RequestBody Notificacion notificacion){
        Notificacion nuevaNotificacion = service.guardar(notificacion);
        return ResponseEntity.ok(nuevaNotificacion);
    }

}
