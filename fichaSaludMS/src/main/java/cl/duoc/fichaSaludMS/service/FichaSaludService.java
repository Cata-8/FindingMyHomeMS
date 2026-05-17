package cl.duoc.fichaSaludMS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.fichaSaludMS.client.MascotaClient;
import cl.duoc.fichaSaludMS.dto.FichaSaludDTO;
import cl.duoc.fichaSaludMS.dto.MascotaDTO;
import cl.duoc.fichaSaludMS.dto.RegistroVacunaDTO;
import cl.duoc.fichaSaludMS.model.FichaSalud;
import cl.duoc.fichaSaludMS.model.RegistroVacuna;
import cl.duoc.fichaSaludMS.repository.FichaSaludRepository;

@Service
public class FichaSaludService {

    @Autowired
    private FichaSaludRepository repo;

    @Autowired
    private MascotaClient mascotaClient;


    public List<FichaSalud> listar(){
        return repo.findAll();
    }

    public FichaSalud buscarPorId(Integer id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha no encontrada"));
    }

    public FichaSalud guardar(FichaSalud fichaSalud){
        MascotaDTO mascota = mascotaClient.obtenerMascotaDTO(fichaSalud.getIdMascota());

        if (mascota == null){
            throw new RuntimeException("Mascota no existe");
        }

        return repo.save(fichaSalud);
    }

    public void eliminar(Integer id){
        if (!repo.existsById(id)) {
            throw new RuntimeException("Ficha no existe");
        }
        repo.deleteById(id);
    }

    public FichaSaludDTO obtenerDetalle(Integer id){

        FichaSalud fichaSalud = repo.findById(id).orElseThrow(() -> new RuntimeException("Ficha no encontrada"));

        MascotaDTO mascota = mascotaClient.obtenerMascotaDTO(fichaSalud.getIdMascota());

        if (mascota == null){
            throw new RuntimeException("Mascota no existe");
        }

        List<RegistroVacuna> registro = fichaSalud.getRegistroVacunas();

        List<RegistroVacunaDTO> registroDTO = new ArrayList<>();

        for(RegistroVacuna registro1 : registro){

        RegistroVacunaDTO regisDTO = new RegistroVacunaDTO();

        regisDTO.setIdVacuna(registro1.getVacuna().getId());
        regisDTO.setDosis(registro1.getDosis());
        regisDTO.setFecha(registro1.getFecha());


        registroDTO.add(regisDTO);
        }


        FichaSaludDTO detalle = new FichaSaludDTO();

        detalle.setId(fichaSalud.getId());
        detalle.setEnfermedades(fichaSalud.getEnfermedades());
        detalle.setEstadoReproductivo(fichaSalud.getEstadoReproductivo());
        detalle.setDesparasitado(false);
        detalle.setMicroship(false);

        detalle.setIdMascota(mascota.getId());

        detalle.setVacunas(registroDTO);

        return detalle;
    }

    
}
