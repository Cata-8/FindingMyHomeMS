package cl.duoc.UsuarioMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
