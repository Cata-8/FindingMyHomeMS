package cl.duoc.Autenticacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Autenticacion.dto.AutenticacionDTO;
import cl.duoc.Autenticacion.model.Autenticacion;
import cl.duoc.Autenticacion.service.AutenticacionService;

@RestController
@RequestMapping("/api/auth")
public class AutenticacionController {

    @Autowired
    private AutenticacionService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody AutenticacionDTO dto){
        try{
            Autenticacion auth = authService.registrar(dto);
            return ResponseEntity.ok(auth);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AutenticacionDTO dto){
        try{
            String resultado = authService.login(dto);
            return ResponseEntity.ok(resultado);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error en login");
        }
    }

    
}
