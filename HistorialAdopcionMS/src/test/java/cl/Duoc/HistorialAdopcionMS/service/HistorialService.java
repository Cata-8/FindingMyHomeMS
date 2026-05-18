package cl.Duoc.HistorialAdopcionMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.Duoc.HistorialAdopcionMS.client.MascotaClient;
import cl.Duoc.HistorialAdopcionMS.client.UsuarioClient;
import cl.Duoc.HistorialAdopcionMS.dto.HistorialDTO;
import cl.Duoc.HistorialAdopcionMS.dto.MascotaDTO;
import cl.Duoc.HistorialAdopcionMS.model.HistorialAdopcion;
import cl.Duoc.HistorialAdopcionMS.repository.HistorialRepository;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository histoRepo;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private MascotaClient mascotaClient;

    public HistorialAdopcion crearHistorial(HistorialDTO dto) {

        try {
            usuarioClient.buscarUsuario(dto.getIdUsuario());
        } catch (Exception e) {
            throw new RuntimeException("Usuario no existe");
        }

        MascotaDTO mascota;

        try {
            mascota = mascotaClient.buscarMascota(dto.getIdMascota());
        } catch (Exception e) {
            throw new RuntimeException("Mascota no existe");
        }

        if (!mascota.getEstado().equalsIgnoreCase("adoptado")) {
            throw new RuntimeException("La mascota aún no está adoptada");
        }

        HistorialAdopcion historial = new HistorialAdopcion();

        historial.setIdUsuario(dto.getIdUsuario());
        historial.setIdMascota(dto.getIdMascota());

        return histoRepo.save(historial);
    }

    public List<HistorialAdopcion> listar() {
        return histoRepo.findAll();
    }

    public HistorialAdopcion buscarPorId(Integer id) {

        return histoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
    }

    public void eliminar(Integer id) {

        HistorialAdopcion historial = histoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));

        histoRepo.delete(historial);
    }


}
