package cl.duoc.Autenticacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Autenticacion.dto.AutenticacionDTO;
import cl.duoc.Autenticacion.service.AutenticacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Operaciones de registro e inicio de sesión de usuarios")
public class AutenticacionController {

    @Autowired
    private AutenticacionService authService;

    @PostMapping("/register")
    @Operation(summary = "Registrar autenticación", description = "Registra la autenticación inicial de un usuario, verificando previamente que exista en el microservicio de Usuario")
    public ResponseEntity<?> registrar(@RequestBody AutenticacionDTO dto) {
        try {
            return ResponseEntity.ok(authService.registrar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Registra el inicio de sesión de un usuario, verificando previamente que exista en el microservicio de Usuario")
    public ResponseEntity<?> login(@RequestBody AutenticacionDTO dto) {
        try {
            String resultado = authService.login(dto);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en login");
        }
    }

    
}
