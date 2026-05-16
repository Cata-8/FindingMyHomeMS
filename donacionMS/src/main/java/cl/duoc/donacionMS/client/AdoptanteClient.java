package cl.duoc.donacionMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.donacionMS.dto.AdoptanteDTO;

@FeignClient(name = "adoptanteMS", url = "http://localhost:8081")
public interface AdoptanteClient {

    @GetMapping("/api/usuarios/{id}")
    AdoptanteDTO obtenerAdoptante(@PathVariable("id") Integer idUsuario);
}
