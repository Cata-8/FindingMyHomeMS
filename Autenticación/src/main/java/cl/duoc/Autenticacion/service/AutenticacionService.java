package cl.duoc.Autenticacion.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Autenticacion.dto.AutenticacionDTO;
import cl.duoc.Autenticacion.model.Autenticacion;
import cl.duoc.Autenticacion.repository.AutenticacionRepository;

@Service
public class AutenticacionService {

    @Autowired
    private AutenticacionRepository authRepo;


    public Autenticacion registrar(AutenticacionDTO dto){

        Autenticacion auth = new Autenticacion();
        auth.setIdUsuario(dto.getIdUsuario());

        auth.setPasswordHash(dto.getPassword()); 

        auth.setEstado("activo");

        return authRepo.save(auth);
    }


    public String login(AutenticacionDTO dto) {

        Autenticacion auth = authRepo.findByIdUsuario(dto.getIdUsuario());

        if(auth == null){
            return "Usuario no encontrado";
        }
        if(!auth.getEstado().equals("activo")){
            return "Usuario bloqueado";
        }
        if(!auth.getPasswordHash().equals(dto.getPassword())){
            return "Contraseña incorrecta";
        }
        auth.setUltimoLogin(LocalDateTime.now());
        authRepo.save(auth);

        return "Login exitoso";
    }

    
}
