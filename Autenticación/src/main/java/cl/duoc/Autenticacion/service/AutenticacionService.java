package cl.duoc.Autenticacion.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Autenticacion.Client.UsuarioClient;
import cl.duoc.Autenticacion.dto.AutenticacionDTO;
import cl.duoc.Autenticacion.dto.UsuarioDTO;
import cl.duoc.Autenticacion.model.Autenticacion;
import cl.duoc.Autenticacion.repository.AutenticacionRepository;

@Service
public class AutenticacionService {

    @Autowired
    private AutenticacionRepository authRepo;

    @Autowired
    private UsuarioClient usuarioClient;

    public Autenticacion registrar(AutenticacionDTO dto) {
        UsuarioDTO usuario = usuarioClient.buscarUsuario(dto.getIdUsuario());
        if (usuario == null) {
            throw new RuntimeException("El usuario no existe en UsuarioMS");
        }

        Autenticacion existente = authRepo.findByIdUsuario(dto.getIdUsuario());
        if (existente != null) {
            return existente;
        }

        Autenticacion auth = new Autenticacion();
        auth.setIdUsuario(dto.getIdUsuario());
        auth.setEstado("activo");
        return authRepo.save(auth);
    }

    public String login(AutenticacionDTO dto) {
        UsuarioDTO usuario = usuarioClient.buscarUsuario(dto.getIdUsuario());
        if (usuario == null) {
            return "Usuario no encontrado en UsuarioMS";
        }

        Autenticacion auth = authRepo.findByIdUsuario(dto.getIdUsuario());
        if (auth == null) {
            return "Usuario no encontrado";
        }
        if (!auth.getEstado().equals("activo")) {
            return "Usuario bloqueado";
        }
        auth.setUltimoLogin(LocalDateTime.now());
        authRepo.save(auth);
        return "Login exitoso";
    }

    
}
