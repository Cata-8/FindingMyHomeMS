package Duoc.cl.SolicitudMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mascota-service", url = "http://localhost:8087")
public interface MascotaClient {

    @GetMapping("/api/mascotas/{id}")
    Object buscarMascota(@PathVariable Integer id);
}
