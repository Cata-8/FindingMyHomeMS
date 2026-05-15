package cl.duoc.mascotaMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.mascotaMS.model.Mascota;
import cl.duoc.mascotaMS.repository.MascotaRepository;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository repo;

    public List<Mascota> listar(){
        return repo.findAll();
    }

    public Mascota buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
    }

    public Mascota buscarPorEstado(String estado){
        return repo.findByEstado(estado).orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
    }

    public Mascota guardar(Mascota mascota){
        return repo.save(mascota);
    }

    public void eliminar(Integer id){
        if(!repo.existsById(id)){
            throw new RuntimeException("Mascota no existe"); 
        }
        repo.deleteById(id);
    }
}
