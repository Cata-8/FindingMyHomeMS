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
import cl.duoc.UsuarioMS.model.Adoptante;
import cl.duoc.UsuarioMS.model.Refugio;
import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name= "Usuarios", description= "Openaciones sobre usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService Uservice;


    @PostMapping("/adoptante")
    @Operation(summary = "Crear adoptante", description = "Registra un nuevo usuario de tipo adoptante")
    public ResponseEntity<?> crearAdoptante(@RequestBody AdoptanteDTO dto){
        try{
            return ResponseEntity.ok(Uservice.guardarAdoptante(dto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/refugio")
    @Operation(summary = "Crear refugio", description = "Registra un nuevo usuario de tipo refugio")
    public ResponseEntity<?> crearRefugio(@RequestBody RefugioDTO dto){
        try{
            return ResponseEntity.ok(Uservice.guardarRefugio(dto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna la lista completa de usuarios registrados")
    public ResponseEntity<?> listarUsuarios() {
        try{
            List<Usuario> lista = Uservice.listarUsuarios();
            return ResponseEntity.ok(lista);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    @Operation(summary= "Buscar usuario por ID", description = "Retorna un usuario segun el ID proporcionado")
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
    @Operation(summary = "Eliminar usuario por ID", description = "Elimina un usuario segun el ID proporcionado")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try{
            Uservice.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/dto/adoptante/{id}")
    @Operation(summary = "Retorna un Adoptante DTO",
            description = "Retorna los datos de un adoptante segun el id del usuario, este metodo se utiliza para ser llamado desde otros microservicios cuando se necesite los datos de un adoptante")
    public ResponseEntity<AdoptanteDTO> obtenerAdoptanteDTO(@PathVariable Integer id) {

    Usuario usuario = Uservice.buscarUsuario(id);

    Adoptante adoptante = usuario.getAdoptante();

    if (adoptante == null) {
        return ResponseEntity.notFound().build();
        }
        
    AdoptanteDTO dto = new AdoptanteDTO(
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getEmail(),
            usuario.getTelefono(),
            null 
    );
    return ResponseEntity.ok(dto);
    }

    @GetMapping("/dto/refugio/{id}")
    @Operation(summary = "Retorna un Refugio DTO",
           description = "Retorna los datos de un refugio segun el id del usuario, este metodo se utiliza para ser llamado desde otros microservicios cuando se necesite los datos de un refugio")
    public ResponseEntity<RefugioDTO> obtenerRefugioDTO(@PathVariable Integer id) {

        Usuario usuario = Uservice.buscarUsuario(id);

        Refugio refugio = usuario.getRefugio();

        if (refugio == null) {
            return ResponseEntity.notFound().build();
            }

        RefugioDTO dto = new RefugioDTO(
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getEmail(),
            usuario.getTelefono(),
            null, 
            refugio.getNombreRefugio(),
            refugio.getDireccion(),
            refugio.getDescripcion(),
            refugio.getTelefonoContacto()
        );
        return ResponseEntity.ok(dto);
    }

}
