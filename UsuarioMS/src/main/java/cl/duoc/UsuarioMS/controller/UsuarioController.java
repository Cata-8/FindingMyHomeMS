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

import cl.duoc.UsuarioMS.model.Adoptante;
import cl.duoc.UsuarioMS.model.Refugio;
import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService Uservice;

    @PostMapping("/adoptante")
    public ResponseEntity<?> crearAdoptante(@RequestBody Usuario usuario) {
        try{
            Adoptante adoptante = Uservice.guardarAdoptante(usuario);
            return ResponseEntity.ok(adoptante);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al registrar adoptante");
        }
    }

    @PostMapping("/refugio")
    public ResponseEntity<?> crearRefugio(@RequestBody Refugio refugio) {
        try{
            Usuario usuario = refugio.getUsuario();
            Refugio nuevo = Uservice.guardarRefugio(usuario, refugio);
            return ResponseEntity.ok(nuevo);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al registrar refugio");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try{
            List<Usuario> lista = Uservice.listarUsuarios();
            return ResponseEntity.ok(lista);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al obtener usuarios");
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
            return ResponseEntity.badRequest().body("Error al buscar usuario");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try{
            Uservice.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al eliminar usuario");
        }
    }

}
