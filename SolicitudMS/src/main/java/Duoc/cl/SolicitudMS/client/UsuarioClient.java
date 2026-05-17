package Duoc.cl.SolicitudMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://localhost:8081")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    Object buscarUsuario(@PathVariable Integer id);
}
