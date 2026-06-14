package cl.duoc.mascotaMS.controller;

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

import cl.duoc.mascotaMS.dto.MascotaDTO;
import cl.duoc.mascotaMS.model.Mascota;
import cl.duoc.mascotaMS.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/mascota")
@Tag(name = "Mascotas", description = "Operaciones sobre mascotas")
public class MascotaController {

    @Autowired
    private MascotaService service;

    @GetMapping
    @Operation(summary = "Llama a todas las mascotas ingresadas en el sistema")
    public ResponseEntity<List<Mascota>> listar(){
        List<Mascota> lista = service.listar();

        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> buscarPorId(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Mascota> buscarPorEstado(@PathVariable String estado){
        try {
            return ResponseEntity.ok(service.buscarPorEstado(estado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Mascota> guardar(@RequestBody Mascota mascota){
        return ResponseEntity.ok(service.guardar(mascota));
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

    @GetMapping("/dto/{id}")
    public ResponseEntity<MascotaDTO> obtenerMascotaDTO(@PathVariable Integer id){
        Mascota mascota = service.buscarPorId(id);
        MascotaDTO dto = new MascotaDTO(
                        mascota.getId(),
                        mascota.getNombre(),
                        mascota.getTipo(),
                        mascota.getEstado()
        );
        return ResponseEntity.ok(dto);

    }
}
