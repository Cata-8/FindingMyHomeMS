package cl.duoc.UsuarioMS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock //simulación
    private UsuarioRepository repoUsuario;

    @InjectMocks //servicio real con el repo
    private UsuarioService servUsuario;

    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp(){
        
        usuarioEjemplo = new Usuario(); 
        usuarioEjemplo.setIdUsuario(1);
        usuarioEjemplo.setNombre("Juan");
        usuarioEjemplo.setApellido("Pérez");
        usuarioEjemplo.setTelefono("2353636322");
        usuarioEjemplo.setEmail("juanperez@gmail.com");
    }

    @Test
    void buscarUsuario_encontrado(){

        //ARRANGE = preparamos la prueba
        Optional<Usuario> optionalUsuario = Optional.of(usuarioEjemplo);
        when(repoUsuario.findById(1)).thenReturn(optionalUsuario);

        //ACT = llamamos al metodo real
        Usuario resultado = servUsuario.buscarUsuario(1);

        //ASSERT = verificación
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
        assertEquals("2353636322", resultado.getTelefono());
        assertEquals("juanperez@gmail.com", resultado.getEmail());

    }

    @Test
    void buscarUsuario_noEncontrado(){
        //ARRANGE = preparamos la prueba para que devuelva vacio
        Optional<Usuario> usuarioVacio = Optional.empty();
        when(repoUsuario.findById(99)).thenReturn(usuarioVacio);

        //ACT + ASSERT = verificando la exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servUsuario.buscarUsuario(99);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void guardarAdoptante(){
        
    }

}
