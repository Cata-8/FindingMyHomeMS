package cl.duoc.UsuarioMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.UsuarioMS.client.AutenticacionClient;
import cl.duoc.UsuarioMS.dto.AdoptanteDTO;
import cl.duoc.UsuarioMS.dto.AutenticacionDTO;
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

    @Autowired
    private AutenticacionClient authClient;


    public List<Usuario> listarUsuarios(){
        return usuarioRepo.findAll();
    }

    public Usuario buscarUsuario(Integer id){
    if(id == null){
        throw new RuntimeException("El id no puede ser null");
    }
    return usuarioRepo.findById(id).orElse(null);
}

    public void eliminarUsuario(Integer id){
    if(id == null){
        throw new RuntimeException("El id no puede ser null");
    }
    usuarioRepo.deleteById(id);
}


        public Adoptante guardarAdoptante(AdoptanteDTO dto){

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        AutenticacionDTO authDTO = new AutenticacionDTO(
                usuarioGuardado.getIdUsuario(),
                dto.getPassword()
        );

        authClient.registrar(authDTO);

        Adoptante adoptante = new Adoptante();
        adoptante.setUsuario(usuarioGuardado);

        return adoptanteRepo.save(adoptante);
    }

    
    public Refugio guardarRefugio(RefugioDTO dto){

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        AutenticacionDTO authDTO = new AutenticacionDTO(
                usuarioGuardado.getIdUsuario(),
                dto.getPassword()
        );

        authClient.registrar(authDTO);

        Refugio refugio = new Refugio();
        refugio.setUsuario(usuarioGuardado);
        refugio.setNombreRefugio(dto.getNombreRefugio());
        refugio.setDireccion(dto.getDireccion());
        refugio.setDescripcion(dto.getDescripcion());
        refugio.setTelefonoContacto(dto.getTelefonoContacto());

        return refugioRepo.save(refugio);
    }

}
