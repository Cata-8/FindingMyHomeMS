package Duoc.cl.SolicitudMS.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Duoc.cl.SolicitudMS.client.MascotaClient;
import Duoc.cl.SolicitudMS.client.UsuarioClient;
import Duoc.cl.SolicitudMS.dto.SolicitudDTO;
import Duoc.cl.SolicitudMS.model.Solicitud;
import Duoc.cl.SolicitudMS.repository.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepo;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private MascotaClient mascotaClient;

    
    public Solicitud crear(SolicitudDTO dto){

    if(usuarioClient.buscarUsuario(dto.getIdUsuarioAdoptante()) == null){
        throw new RuntimeException("Usuario no existe");
    }

    if(mascotaClient.buscarMascota(dto.getIdMascota()) == null){
        throw new RuntimeException("Mascota no existe");
    }

    Solicitud sol = new Solicitud();
    sol.setMensaje(dto.getMensaje());
    sol.setIdMascota(dto.getIdMascota());
    sol.setIdUsuarioAdoptante(dto.getIdUsuarioAdoptante());
    sol.setEstado("pendiente");
    sol.setFechaSolicitud(LocalDateTime.now());

    return solicitudRepo.save(sol);
    }

    public List<Solicitud> listar(){
        return solicitudRepo.findAll();
    }
}
