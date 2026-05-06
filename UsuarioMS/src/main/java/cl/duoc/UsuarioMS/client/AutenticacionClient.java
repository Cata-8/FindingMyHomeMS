package cl.duoc.UsuarioMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.duoc.UsuarioMS.dto.AutenticacionDTO;

@FeignClient(name = "auth-service", url = "http://localhost:8084")
public interface AutenticacionClient {

    @PostMapping("/api/auth/register")
    void registrar(@RequestBody AutenticacionDTO dto);
}
