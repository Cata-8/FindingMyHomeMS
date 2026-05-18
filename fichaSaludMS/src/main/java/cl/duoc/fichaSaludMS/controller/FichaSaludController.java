package cl.duoc.fichaSaludMS.controller;

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

import cl.duoc.fichaSaludMS.model.FichaSalud;
import cl.duoc.fichaSaludMS.service.FichaSaludService;

@RestController
@RequestMapping("/api/v1/fichaSalud")
public class FichaSaludController {

    @Autowired
    private FichaSaludService service;

    @GetMapping
    public ResponseEntity<List<FichaSalud>> listar(){
        List<FichaSalud> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } 

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichaSalud> buscar(@PathVariable Integer id){
        try {
            FichaSalud fichaSalud = service.buscarPorId(id);
            return ResponseEntity.ok(fichaSalud);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FichaSalud> guardar(@RequestBody FichaSalud fichaSalud){
        FichaSalud nueva = service.guardar(fichaSalud);
        return ResponseEntity.ok(nueva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<FichaSaludDTO> detalle(@PathVariable Integer id){
        try {
            FichaSaludDTO dto = service.obtenerDetalle(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
