package cl.duoc.Autenticacion.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.Autenticacion.dto.AutenticacionDTO;
import cl.duoc.Autenticacion.model.Autenticacion;
import cl.duoc.Autenticacion.service.AutenticacionService;

@WebMvcTest(AutenticacionController.class)
public class AutenticacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AutenticacionService authService;

    private Autenticacion authEjemplo;

    @BeforeEach
    void setUp() {
        authEjemplo = new Autenticacion();
        authEjemplo.setIdAuth(1);
        authEjemplo.setIdUsuario(1);
        authEjemplo.setEstado("activo");
    }

    @Test
    void registrar_retorna200() throws Exception {
        // ARRANGE
        when(authService.registrar(any(AutenticacionDTO.class))).thenReturn(authEjemplo);

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("activo"))
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    void registrar_retorna400CuandoUsuarioNoExiste() throws Exception {
        // ARRANGE
        when(authService.registrar(any(AutenticacionDTO.class)))
                .thenThrow(new RuntimeException("El usuario no existe en UsuarioMS"));

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\":99}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_retorna200Exitoso() throws Exception {
        // ARRANGE
        when(authService.login(any(AutenticacionDTO.class))).thenReturn("Login exitoso");

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login exitoso"));
    }

    @Test
    void login_retorna200UsuarioBloqueado() throws Exception {
        // ARRANGE: el servicio devuelve "Usuario bloqueado" (no lanza excepción)
        when(authService.login(any(AutenticacionDTO.class))).thenReturn("Usuario bloqueado");

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario bloqueado"));
    }

    @Test
    void login_retorna400CuandoLanzaExcepcion() throws Exception {
        // ARRANGE
        when(authService.login(any(AutenticacionDTO.class)))
                .thenThrow(new RuntimeException("Error inesperado"));

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\":1}"))
                .andExpect(status().isBadRequest());
    }
}
