package cl.duoc.UsuarioMS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import cl.duoc.UsuarioMS.dto.AdoptanteDTO;
import cl.duoc.UsuarioMS.dto.RefugioDTO;
import cl.duoc.UsuarioMS.model.Adoptante;
import cl.duoc.UsuarioMS.model.Refugio;
import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.service.UsuarioService;

@WebMvcTest(UsuarioController.class) //levanta capa web no la base de datos
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mock; //simula las peticiones http

    @MockitoBean
    private UsuarioService serviceU; //service falso 

    private Usuario usuarioEjemplo;
    private Adoptante adoptanteEjemplo;
    private Refugio refugioEjemplo;

    @BeforeEach
    void setUp(){

        usuarioEjemplo = new Usuario(); 
        usuarioEjemplo.setIdUsuario(1);
        usuarioEjemplo.setNombre("Juan");
        usuarioEjemplo.setApellido("Pérez");
        usuarioEjemplo.setTelefono("2353636322");
        usuarioEjemplo.setEmail("juanperez@gmail.com");

        adoptanteEjemplo = new Adoptante();
        adoptanteEjemplo.setUsuario(usuarioEjemplo);
 
        refugioEjemplo = new Refugio();
        refugioEjemplo.setUsuario(usuarioEjemplo);
        refugioEjemplo.setNombreRefugio("Patitas Felices");
        refugioEjemplo.setDireccion("Av. Siempre Viva 123");
        refugioEjemplo.setDescripcion("Refugio de animales abandonados");
        refugioEjemplo.setTelefonoContacto("222333444");
    }

    @Test
    void listar_retorna200ConUsuarios() throws Exception {
        // ARRANGE
        List<Usuario> listaFalsa = new ArrayList<>();
        listaFalsa.add(usuarioEjemplo);
        when(serviceU.listarUsuarios()).thenReturn(listaFalsa);
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idUsuario").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Juan"))
            .andExpect(jsonPath("$[0].email").value("juanperez@gmail.com"));
    }

    @Test
    void buscarUsuario_retorna200() throws Exception{
        //ARRANGE = debe retornar usuario
        when(serviceU.buscarUsuario(1)).thenReturn(usuarioEjemplo);

        //ACT + ASSERT = retorna un 200
        mock.perform(get("/api/v1/usuarios/1")).andExpect(status().isOk());

    }

    @Test
    void buscarUsuario_retorna404() throws Exception{
        // ARRANGE: el service retorna null (usuario no existe)
        when(serviceU.buscarUsuario(99)).thenReturn(null);
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());

    }

    @Test
    void eliminarUsuario_retorna200() throws Exception {
        // ARRANGE
        doNothing().when(serviceU).eliminarUsuario(1);
 
        // ACT + ASSERT
        mock.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isOk());
    }
 
    @Test
    void eliminarUsuario_retorna400() throws Exception {
        // ARRANGE
        doThrow(new RuntimeException("Usuario no encontrado")).when(serviceU).eliminarUsuario(99);
 
        // ACT + ASSERT
        mock.perform(delete("/api/v1/usuarios/99"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void crearAdoptante_retorna200() throws Exception {
        // ARRANGE
        when(serviceU.guardarAdoptante(any(AdoptanteDTO.class))).thenReturn(adoptanteEjemplo);
 
        // ACT + ASSERT
        mock.perform(post("/api/v1/usuarios/adoptante")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan\",\"apellido\":\"Pérez\","
                        + "\"email\":\"juanperez@gmail.com\","
                        + "\"telefono\":\"912345678\",\"password\":\"1234\"}"))
                .andExpect(status().isOk());
    }
 
    @Test
    void crearAdoptante_retorna400() throws Exception {
        // ARRANGE
        when(serviceU.guardarAdoptante(any(AdoptanteDTO.class)))
                .thenThrow(new RuntimeException("Error al registrar en auth"));
 
        // ACT + ASSERT
        mock.perform(post("/api/v1/usuarios/adoptante")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan\",\"apellido\":\"Pérez\","
                        + "\"email\":\"juanperez@gmail.com\","
                        + "\"telefono\":\"912345678\",\"password\":\"1234\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearRefugio_retorna200() throws Exception {
        // ARRANGE
        when(serviceU.guardarRefugio(any(RefugioDTO.class))).thenReturn(refugioEjemplo);
 
        // ACT + ASSERT
        mock.perform(post("/api/v1/usuarios/refugio")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombre\":\"Ana\",\"apellido\":\"López\","
                    + "\"email\":\"refugio@gmail.com\","
                    + "\"telefono\":\"987654321\",\"password\":\"abcd\","
                    + "\"nombreRefugio\":\"Patitas Felices\","
                    + "\"direccion\":\"Av. Siempre Viva 123\","
                    + "\"descripcion\":\"Refugio de animales\","
                    + "\"telefonoContacto\":\"222333444\"}"))
                .andExpect(status().isOk());
    }
 
    @Test
    void crearRefugio_retorna400() throws Exception {
        // ARRANGE
        when(serviceU.guardarRefugio(any(RefugioDTO.class)))
                .thenThrow(new RuntimeException("Error al registrar en auth"));
 
        // ACT + ASSERT
        mock.perform(post("/api/v1/usuarios/refugio")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombre\":\"Ana\",\"apellido\":\"López\","
                + "\"email\":\"refugio@gmail.com\","
                + "\"telefono\":\"987654321\",\"password\":\"abcd\","
                + "\"nombreRefugio\":\"Patitas Felices\","
                + "\"direccion\":\"Av. Siempre Viva 123\","
                + "\"descripcion\":\"Refugio de animales\","
                + "\"telefonoContacto\":\"222333444\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerAdoptanteDTO_retorna200() throws Exception {
        // ARRANGE: el usuario tiene un adoptante asociado
        usuarioEjemplo.setAdoptante(adoptanteEjemplo);
        when(serviceU.buscarUsuario(1)).thenReturn(usuarioEjemplo);
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/usuarios/dto/adoptante/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan"))
            .andExpect(jsonPath("$.email").value("juanperez@gmail.com"));
    }
 
    @Test
    void obtenerAdoptanteDTO_retorna404() throws Exception {
        // ARRANGE: el usuario existe pero no tiene adoptante
        usuarioEjemplo.setAdoptante(null);
        when(serviceU.buscarUsuario(1)).thenReturn(usuarioEjemplo);
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/usuarios/dto/adoptante/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void obtenerRefugioDTO_retorna200() throws Exception {
        // ARRANGE: el usuario tiene un refugio asociado
        usuarioEjemplo.setRefugio(refugioEjemplo);
        when(serviceU.buscarUsuario(1)).thenReturn(usuarioEjemplo);
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/usuarios/dto/refugio/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreRefugio").value("Patitas Felices"))
            .andExpect(jsonPath("$.direccion").value("Av. Siempre Viva 123"));
    }
 
    @Test
    void obtenerRefugioDTO_retorna404() throws Exception {
        // ARRANGE: el usuario existe pero no tiene refugio
        usuarioEjemplo.setRefugio(null);
        when(serviceU.buscarUsuario(1)).thenReturn(usuarioEjemplo);
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/usuarios/dto/refugio/1"))
            .andExpect(status().isNotFound());
    }
}
