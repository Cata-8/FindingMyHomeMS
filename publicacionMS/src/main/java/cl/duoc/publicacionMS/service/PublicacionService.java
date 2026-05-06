package cl.duoc.publicacionMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.publicacionMS.client.MascotaClient;
import cl.duoc.publicacionMS.dto.MascotaDTO;
import cl.duoc.publicacionMS.dto.PublicacionDTO;
import cl.duoc.publicacionMS.model.Publicacion;
import cl.duoc.publicacionMS.repository.PublicacionRepository;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository repo;

    @Autowired
    private MascotaClient mascotaClient;

    public List<Publicacion> listar(){
        return repo.findAll();
    }

    public Publicacion guardar(Publicacion publicacion){
        MascotaDTO mascota = mascotaClient.obtenerMascotaDTO(publicacion.getIdMascota());

        if (mascota == null) {
            throw new RuntimeException("mascota no existe");
        }

        return repo.save(publicacion);
    }

    public Publicacion buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
    }

    public PublicacionDTO obtenerDetalle(Integer id){
        Publicacion publicacion = repo.findById(id).orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        MascotaDTO mascota = mascotaClient.obtenerMascotaDTO(publicacion.getIdMascota());

        if (mascota == null) {
            throw new RuntimeException("Mascota no encontrada");
        }

        PublicacionDTO dto = new PublicacionDTO();

        dto.setId(publicacion.getId());
        dto.setTitulo(publicacion.getTitulo());
        dto.setFechaPublicacion(publicacion.getFechaPublicacion());
        dto.setMascota(mascota);

        return dto;
    }

    public void eliminar(Integer id){
        if (!repo.existsById(id)) {
            throw new RuntimeException("Publicacion no existe");
        }
        repo.deleteById(id);
    }
}
