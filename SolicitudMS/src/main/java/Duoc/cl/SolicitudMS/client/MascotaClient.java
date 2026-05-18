package Duoc.cl.SolicitudMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import Duoc.cl.SolicitudMS.dto.MascotaDTO;

@FeignClient(name = "mascota-service", url = "http://localhost:8087")
public interface MascotaClient {

    @GetMapping("/api/v1/mascota/{id}")
    MascotaDTO buscarMascota(@PathVariable("id") Integer id);
}
