package cl.duoc.donacionMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.donacionMS.dto.DonacionDetalleDTO;
import cl.duoc.donacionMS.model.Donacion;
import cl.duoc.donacionMS.service.DonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/donaciones")
@Tag(name = "Donaciones", description = "Operaciones sobre las donaciones realizadas por usuarios")
public class DonacionController {
    @Autowired
    private DonacionService service;

    @GetMapping
    @Operation(summary = "Listar donaciones", description = "Retorna todas las donaciones registradas en el sistema")
    public ResponseEntity<List<Donacion>> listar() {
        List<Donacion> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar donación por ID", description = "Retorna una donación según el ID proporcionado")
    public ResponseEntity<Donacion> buscar(
            @Parameter(description = "ID de la donación a buscar") @PathVariable Integer id) {
        try {
            Donacion donacion = service.buscarPorId(id);
            return ResponseEntity.ok(donacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Retorna un detalle de Donación",
            description = "Retorna los datos basicos de una donación segun su id, este metodo se utiliza para ser llamado desde otros microservicios cuando se necesiten los datos de una donación")
    public ResponseEntity<DonacionDetalleDTO> detalle(
            @Parameter(description = "ID de la donación") @PathVariable Integer id) {
        try {
            Donacion donacion = service.buscarPorId(id);
            DonacionDetalleDTO dto = new DonacionDetalleDTO(
                    donacion.getId(),
                    donacion.getFecha(),
                    donacion.getIdUsuario()
            );
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear donación", description = "Registra una nueva donación realizada por un usuario")
    public ResponseEntity<Donacion> guardar(@RequestBody Donacion donacion) {
        try {
            Donacion nuevaDonacion = service.guardar(donacion);
            return ResponseEntity.ok(nuevaDonacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
