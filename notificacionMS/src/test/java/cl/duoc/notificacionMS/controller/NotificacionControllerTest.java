package cl.duoc.notificacionMS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.notificacionMS.dto.NotificacionDTO;
import cl.duoc.notificacionMS.model.Notificacion;
import cl.duoc.notificacionMS.service.NotificacionService;

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService service;

    private Notificacion notificacionEjemplo;

    @BeforeEach
    void setUp() {
        notificacionEjemplo = new Notificacion();
        notificacionEjemplo.setId(1);
        notificacionEjemplo.setMensaje("Tu solicitud fue aprobada");
        notificacionEjemplo.setFechaEmision(new Date());
        notificacionEjemplo.setUsuarioId(1);
    }

    @Test
    void listar_retorna200ConNotificaciones() throws Exception {
        // ARRANGE
        List<Notificacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(notificacionEjemplo);
        when(service.listar()).thenReturn(listaFalsa);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].mensaje").value("Tu solicitud fue aprobada"));
    }

    @Test
    void listar_retorna204SinNotificaciones() throws Exception {
        // ARRANGE
        when(service.listar()).thenReturn(new ArrayList<>());

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscar_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(notificacionEjemplo);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Tu solicitud fue aprobada"));
    }

    @Test
    void buscar_retorna404CuandoNoExiste() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Notificacion no encontrada"));

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/notificaciones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void detalle_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(notificacionEjemplo);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/notificaciones/1/notificacionDetallada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Tu solicitud fue aprobada"))
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    void detalle_retorna404CuandoNoExiste() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Notificacion no encontrada"));

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/notificaciones/99/notificacionDetallada"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_retorna200() throws Exception {
        // ARRANGE
        when(service.guardar(any(Notificacion.class))).thenReturn(notificacionEjemplo);

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mensaje\":\"Tu solicitud fue aprobada\",\"usuarioId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Tu solicitud fue aprobada"));
    }
}