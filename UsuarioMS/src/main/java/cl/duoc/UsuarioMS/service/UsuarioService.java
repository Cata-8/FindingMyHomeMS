package cl.duoc.UsuarioMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.UsuarioMS.dto.AdoptanteDTO;
import cl.duoc.UsuarioMS.dto.RefugioDTO;
import cl.duoc.UsuarioMS.model.Adoptante;
import cl.duoc.UsuarioMS.model.Refugio;
import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.repository.AdoptanteRepository;
import cl.duoc.UsuarioMS.repository.RefugioRepository;
import cl.duoc.UsuarioMS.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private AdoptanteRepository adoptanteRepo;

    @Autowired
    private RefugioRepository refugioRepo;

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public Adoptante guardarAdoptante(Usuario usuario) {
        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        Adoptante adoptante = new Adoptante();
        adoptante.setUsuario(usuarioGuardado);

        return adoptanteRepo.save(adoptante);
    }

    public Refugio guardarRefugio(Usuario usuario, Refugio refugio) {
        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        refugio.setUsuario(usuarioGuardado);

        return refugioRepo.save(refugio);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    public Usuario buscarUsuario(Integer id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepo.deleteById(id);
    }


    public Adoptante guardarAdoptanteDTO(AdoptanteDTO dto){

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        Adoptante adoptante = new Adoptante();
        adoptante.setUsuario(usuarioGuardado);

        return adoptanteRepo.save(adoptante);
    }


    public Refugio guardarRefugioDTO(RefugioDTO dto){

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        Refugio refugio = new Refugio();
        refugio.setUsuario(usuarioGuardado);
        refugio.setNombre_Refugio(dto.getNombreRefugio());
        refugio.setDirección(dto.getDireccion());
        refugio.setDescripción(dto.getDescripcion());
        refugio.setTelefono_Contacto(dto.getTelefonoContacto());

        return refugioRepo.save(refugio);
    }

}
