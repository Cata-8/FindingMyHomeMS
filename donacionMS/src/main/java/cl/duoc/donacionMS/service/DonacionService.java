package cl.duoc.donacionMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.donacionMS.client.UsuarioClient;
import cl.duoc.donacionMS.dto.DonacionDetalleDTO;
import cl.duoc.donacionMS.dto.UsuarioDTO;
import cl.duoc.donacionMS.model.Donacion;
import cl.duoc.donacionMS.repository.DonacionRepository;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository repo;
    @Autowired
    private UsuarioClient usuarioClient;

    public List<Donacion> listar(){
        return repo.findAll();
    }

    public Donacion guardar(Donacion donacion){

        UsuarioDTO usuario = usuarioClient.obtenerUsuario(donacion.getIdUsuario());

        if(usuario == null){
            throw new RuntimeException("Usuario no existe");
        }

        return repo.save(donacion);
    }

    public Donacion buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Donación no encontrada"));
    }

    public DonacionDetalleDTO obtenerDetalle(Integer id){
        Donacion donacion = repo.findById(id).orElseThrow(() -> new RuntimeException("Donación no encontrada"));

        UsuarioDTO usuario = usuarioClient.obtenerUsuario(donacion.getIdUsuario());

        if(usuario == null){
            throw new RuntimeException("Usuario no existe");
        }

        DonacionDetalleDTO dto = new DonacionDetalleDTO();
        dto.setId(donacion.getId());
        dto.setFecha(donacion.getFecha());

        dto.setUsuario(usuario);

        return dto;

    }

}
