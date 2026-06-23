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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/mascota")
@Tag(name = "Mascotas", description = "Operaciones sobre mascotas")
public class MascotaController {

    @Autowired
    private MascotaService service;

    @GetMapping
    @Operation(summary = "Listar mascotas", description = "Retorna todas las mascotas ingresadas en el sistema")
    public ResponseEntity<List<Mascota>> listar() {
        List<Mascota> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mascota por ID", description = "Retorna una mascota según el ID proporcionado")
    public ResponseEntity<Mascota> buscarPorId(
            @Parameter(description = "ID de la mascota a buscar") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar mascotas por estado",
            description = "Retorna el listado de mascotas según el estado proporcionado, ya sea disponible, en adopción, etc.")
    public ResponseEntity<List<Mascota>> buscarPorEstado(
            @Parameter(description = "Estado de la mascota (ej: disponible, adoptado)") @PathVariable String estado) {
        try {
            List<Mascota> lista = service.buscarPorEstado(estado);
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar mascota", description = "Registra una nueva mascota en el sistema")
    public ResponseEntity<Mascota> guardar(@RequestBody Mascota mascota) {
        return ResponseEntity.ok(service.guardar(mascota));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota según el ID ingresado")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la mascota a eliminar") @PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Retorna un Mascota DTO",
               description = "Retorna los datos basicos de una mascota segun su id, este metodo se utiliza para ser llamado desde otros microservicios cuando se necesite los datos de una mascota")
    public ResponseEntity<MascotaDTO> obtenerMascotaDTO(
            @Parameter(description = "ID de la mascota") @PathVariable Integer id) {
        try{
            Mascota mascota = service.buscarPorId(id);
            MascotaDTO dto = new MascotaDTO(
            mascota.getId(),
            mascota.getNombre(),
            mascota.getTipo(),
            mascota.getEstado()
            );
            return ResponseEntity.ok(dto);
        }catch (Exception e) {
        return ResponseEntity.notFound().build();
        }   
    }
}
