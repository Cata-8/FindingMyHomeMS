package cl.duoc.notificacionMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.notificacionMS.dto.UsuarioDTO;

@FeignClient(name = "usuarioMS", url = "http://localhost:8081")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/dto/{id}")
    UsuarioDTO obtenUsuario(@PathVariable("id") Integer id);
}
