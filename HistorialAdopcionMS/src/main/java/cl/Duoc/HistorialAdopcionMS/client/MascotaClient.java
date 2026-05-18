package cl.Duoc.HistorialAdopcionMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.Duoc.HistorialAdopcionMS.dto.MascotaDTO;

@FeignClient(name = "mascotas-ms", url = "http://localhost:8087")
public interface MascotaClient {

    @GetMapping("/api/v1/mascota/{id}")
    MascotaDTO buscarMascota(@PathVariable("id") Integer id);
    
}
