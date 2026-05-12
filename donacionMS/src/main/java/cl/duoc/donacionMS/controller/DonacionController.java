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

@RestController
@RequestMapping("/api/v1/donaciones")
public class DonacionController {
    @Autowired
    private DonacionService service;

    @GetMapping
    public ResponseEntity<List<Donacion>> listar(){
        List<Donacion> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } 

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donacion> buscar(@PathVariable Integer id){
        try {
            Donacion donacion = service.buscarPorId(id);
            return ResponseEntity.ok(donacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<DonacionDetalleDTO> detalle(@PathVariable Integer id){
        try {
            DonacionDetalleDTO dto = service.obtenerDetalle(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Donacion> guardar(@RequestBody Donacion donacion){
        try {
            Donacion nuevaDonacion = service.guardar(donacion);
            return ResponseEntity.ok(nuevaDonacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
