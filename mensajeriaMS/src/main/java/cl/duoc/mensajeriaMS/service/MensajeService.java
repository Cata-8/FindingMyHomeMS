package cl.duoc.mensajeriaMS.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.mensajeriaMS.client.UsuarioClient;
import cl.duoc.mensajeriaMS.dto.MensajeDTO;
import cl.duoc.mensajeriaMS.model.Mensaje;
import cl.duoc.mensajeriaMS.repository.MensajeRepository;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepo;

    @Autowired
    private UsuarioClient usuarioClient;

    public Mensaje crearMensaje(MensajeDTO dto) {

        try {
            usuarioClient.buscarUsuario(dto.getIdRemitente());
            usuarioClient.buscarUsuario(dto.getIdDestinatario());
        } catch (Exception e) {
            throw new RuntimeException("Usuario no existe");
        }

        Mensaje mensaje = new Mensaje();

        mensaje.setIdRemitente(dto.getIdRemitente());
        mensaje.setIdDestinatario(dto.getIdDestinatario());
        mensaje.setContenido(dto.getContenido());
        mensaje.setFechaEnvio(LocalDate.now());
        mensaje.setLeido(false);

        return mensajeRepo.save(mensaje);
    }

    public List<Mensaje> listar() {
        return mensajeRepo.findAll();
    }

    public Mensaje buscarPorId(Integer id) {

        return mensajeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
    }

    public Mensaje marcarComoLeido(Integer id) {

        Mensaje mensaje = mensajeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));

        mensaje.setLeido(true);

        return mensajeRepo.save(mensaje);
    }

    public void eliminar(Integer id) {

        Mensaje mensaje = mensajeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));

        mensajeRepo.delete(mensaje);
    }
}
