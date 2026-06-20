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

import cl.duoc.fichaSaludMS.dto.FichaSaludDTO;
import cl.duoc.fichaSaludMS.model.FichaSalud;
import cl.duoc.fichaSaludMS.service.FichaSaludService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/fichaSalud")
@Tag(name = "Ficha de Salud", description = "Operaciones sobre las fichas médicas de las mascotas")
public class FichaSaludController {

    @Autowired
    private FichaSaludService service;

    @GetMapping
    @Operation(summary = "Listar fichas de salud", description = "Retorna todas las fichas de salud registradas en el sistema")
    public ResponseEntity<List<FichaSalud>> listar() {
        List<FichaSalud> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ficha de salud por ID", description = "Retorna una ficha de salud según el ID proporcionado")
    public ResponseEntity<FichaSalud> buscar(
            @Parameter(description = "ID de la ficha de salud a buscar") @PathVariable Integer id) {
        try {
            FichaSalud fichaSalud = service.buscarPorId(id);
            return ResponseEntity.ok(fichaSalud);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear ficha de salud", description = "Registra una nueva ficha de salud para una mascota")
    public ResponseEntity<FichaSalud> guardar(@RequestBody FichaSalud fichaSalud) {
        FichaSalud nueva = service.guardar(fichaSalud);
        return ResponseEntity.ok(nueva);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ficha de salud", description = "Elimina una ficha de salud según el ID ingresado")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la ficha de salud a eliminar") @PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Retorna un detalle de Ficha de Salud",
            description = "Retorna los datos completos de una ficha de salud, incluyendo el listado de vacunas registradas, segun su id")
    public ResponseEntity<FichaSaludDTO> detalle(
            @Parameter(description = "ID de la ficha de salud") @PathVariable Integer id) {
        try {
            FichaSaludDTO dto = service.obtenerDetalle(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
