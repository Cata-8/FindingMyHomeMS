package cl.duoc.mensajeriaMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.mensajeriaMS.dto.UsuarioDTO;

@FeignClient(name = "usuarios-ms", url = "http://localhost:8081")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDTO buscarUsuario(@PathVariable("id") Integer id);
}
