package cl.duoc.donacionMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.donacionMS.dto.RefugioDTO;

@FeignClient(name = "refugioMS", url = "http://localhost:8081")
public interface RefugioClient {

    @GetMapping("/api/usuarios/{id}")
    RefugioDTO obtenerRefugio(@PathVariable("id") Integer idUsuario);
}
