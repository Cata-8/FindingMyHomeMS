package cl.duoc.donacionMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.donacionMS.client.AdoptanteClient;
import cl.duoc.donacionMS.client.RefugioClient;
import cl.duoc.donacionMS.dto.AdoptanteDTO;
import cl.duoc.donacionMS.dto.DonacionDetalleDTO;
import cl.duoc.donacionMS.dto.RefugioDTO;
import cl.duoc.donacionMS.model.Donacion;
import cl.duoc.donacionMS.repository.DonacionRepository;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository repo;
    @Autowired
    private AdoptanteClient adoptanteClient;
    @Autowired 
    private RefugioClient refugioClient;

    public List<Donacion> listar(){
        return repo.findAll();
    }

    public Donacion guardar(Donacion donacion){

        AdoptanteDTO adoptante = adoptanteClient.obtenerAdoptante(donacion.getIdUsuarioAdoptante());

        if(adoptante == null){
            throw new RuntimeException("Adoptante no existe");
        }

        RefugioDTO refugio = refugioClient.obtenerRefugio(donacion.getIdUsuarioRefugio());

        if (refugio == null) {
            throw new RuntimeException("Refugio no existe");
        }

        return repo.save(donacion);
    }

    public Donacion buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Donación no encontrada"));
    }

    public DonacionDetalleDTO obtenerDetalle(Integer id){
        Donacion donacion = repo.findById(id).orElseThrow(() -> new RuntimeException("Donación no encontrada"));

        AdoptanteDTO adoptante = adoptanteClient.obtenerAdoptante(donacion.getIdUsuarioAdoptante());

        if (adoptante == null) {
            throw new RuntimeException("Adoptante no encontrado");
        }

        RefugioDTO refugio = refugioClient.obtenerRefugio(donacion.getIdUsuarioRefugio());

        if (refugio == null) {
            throw new RuntimeException("Refugio no encontrado");
        }

        DonacionDetalleDTO dto = new DonacionDetalleDTO();
        dto.setId(donacion.getId());
        dto.setFecha(donacion.getFecha());

        dto.setAdoptante(adoptante);
        dto.setRefugio(refugio);

        return dto;

    }

}
