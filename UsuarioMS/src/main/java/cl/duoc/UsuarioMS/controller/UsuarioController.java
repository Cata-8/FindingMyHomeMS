package cl.duoc.UsuarioMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.UsuarioMS.dto.AdoptanteDTO;
import cl.duoc.UsuarioMS.dto.RefugioDTO;
import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService Uservice;

    @PostMapping("/adoptante")
    public ResponseEntity<?> crearAdoptante(@RequestBody AdoptanteDTO dto){
        try{
            return ResponseEntity.ok(Uservice.guardarAdoptante(dto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refugio")
    public ResponseEntity<?> crearRefugio(@RequestBody RefugioDTO dto){
        try{
            return ResponseEntity.ok(Uservice.guardarRefugio(dto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try{
            List<Usuario> lista = Uservice.listarUsuarios();
            return ResponseEntity.ok(lista);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

     @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Integer id) {
        try{
            Usuario usuario = Uservice.buscarUsuario(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try{
            Uservice.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
