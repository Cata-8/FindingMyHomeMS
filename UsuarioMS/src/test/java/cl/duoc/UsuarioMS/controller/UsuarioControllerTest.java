package cl.duoc.UsuarioMS.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.core.joran.action.Action;
import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.service.UsuarioService;

@WebMvcTest(UsuarioController.class) //levanta capa web no la base de datos
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mock; //simula las peticiones http

    @Mock
    private UsuarioService serviceU; //service falso 

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
    void buscarUsuario_retorna200(){
        //ARRANGE = debe retornar usuario
        when(serviceU.buscarUsuario(1)).thenReturn(usuarioEjemplo);

        //ACT + ASSERT = retorna un 200
        mock.perform(get("/api/v1/usuarios/1")).andExpect(status().isOK());

    }




    @Test
    void buscarUsuario_retorna404(){
        //ARRANGE = no deberia retornar usuario
        when(serviceU.buscarUsuario(99)).thenThrow(new RuntimeException("Usuario no encontrado"));

        //ACT + ASSERT = retorna un 404
        mock.perform(get("/api/v1/usuarios/99"))
            .andExpect(status().isNotFound());

    }
}
