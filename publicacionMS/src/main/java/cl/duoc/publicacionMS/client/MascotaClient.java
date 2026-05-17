package cl.duoc.publicacionMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.publicacionMS.dto.MascotaDTO;

@FeignClient(name = "mascotaMS", url = "http://localhost:8087")
public interface MascotaClient {

    @GetMapping("/api/v1/mascota/dto/{id}")
    MascotaDTO obtenerMascotaDTO(@PathVariable("id") Integer id);
}
