package Duoc.cl.SolicitudMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Duoc.cl.SolicitudMS.client.MascotaClient;
import Duoc.cl.SolicitudMS.client.UsuarioClient;
import Duoc.cl.SolicitudMS.dto.MascotaDTO;
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

    
    public Solicitud crearSolicitud(SolicitudDTO dto){

    try{
        usuarioClient.buscarUsuario(dto.getIdUsuarioAdoptante());
    }catch (Exception e){
        throw new RuntimeException("Usuario no existe");
    }

    MascotaDTO mascota;
    try {
        mascota = mascotaClient.buscarMascota(dto.getIdMascota());
    } catch (Exception e) {
        throw new RuntimeException("Mascota no existe");
    }

    if(!mascota.getEstado().equalsIgnoreCase("disponible")){
        throw new RuntimeException("Mascota no disponible");
    }

    Solicitud sol = new Solicitud();
    sol.setMensaje(dto.getMensaje());
    sol.setIdMascota(dto.getIdMascota());
    sol.setIdUsuarioAdoptante(dto.getIdUsuarioAdoptante());
    sol.setEstado("pendiente");

    return solicitudRepo.save(sol);
    }

    public List<Solicitud> listar(){
        return solicitudRepo.findAll();
    }

    public Solicitud buscarPorId(Integer id) {
        return solicitudRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    public Solicitud cambiarEstado(Integer id, String estado) {

        Solicitud s = solicitudRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if(!estado.equalsIgnoreCase("pendiente") &&
        !estado.equalsIgnoreCase("aprobada") &&
        !estado.equalsIgnoreCase("rechazada")) {
        throw new RuntimeException("Estado inválido");
        }

        s.setEstado(estado.toLowerCase());
        return solicitudRepo.save(s);
        }

    public void eliminar(Integer id) {

    Solicitud solicitud = solicitudRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitudRepo.delete(solicitud);
}

}
