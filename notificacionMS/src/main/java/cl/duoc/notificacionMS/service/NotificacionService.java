package cl.duoc.notificacionMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.notificacionMS.client.UsuarioClient;
import cl.duoc.notificacionMS.dto.NotificacionDTO;
import cl.duoc.notificacionMS.dto.UsuarioDTO;
import cl.duoc.notificacionMS.model.Notificacion;
import cl.duoc.notificacionMS.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repo;

    @Autowired
    private UsuarioClient usuarioClient;


    public List<Notificacion> listar(){
        return repo.findAll();
    }


    public Notificacion buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Notificacion no encontrada"));
    }

    public Notificacion guardar(Notificacion notificacion){
        UsuarioDTO usuario = usuarioClient.obtenUsuario(notificacion.getUsuarioId());

        if(usuario == null){
            throw new RuntimeException("Usuario no existe");
        }

        return repo.save(notificacion);
    }

    public NotificacionDTO obtenerNotificacion (Integer id){
        Notificacion notificacion = repo.findById(id).orElseThrow(() -> new RuntimeException("Notificacion no encontrada"));

        UsuarioDTO usuario = usuarioClient.obtenUsuario(notificacion.getUsuarioId());

        if(usuario == null){
            throw new RuntimeException("usuario no encontrado");
        }

        NotificacionDTO dto = new NotificacionDTO();

        dto.setId(notificacion.getId());
        dto.setMensaje(notificacion.getMensaje());
        dto.setFechaEmision(notificacion.getFechaEmision());

        dto.setUsuario(usuario);

        return dto;
    }

}
