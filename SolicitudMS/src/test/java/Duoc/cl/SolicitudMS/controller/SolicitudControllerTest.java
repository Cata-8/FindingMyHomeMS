package Duoc.cl.SolicitudMS.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import Duoc.cl.SolicitudMS.dto.SolicitudDTO;
import Duoc.cl.SolicitudMS.model.Solicitud;
import Duoc.cl.SolicitudMS.service.SolicitudService;

@WebMvcTest(SolicitudController.class)
public class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitudService soliService;

    private Solicitud solicitudEjemplo;

    @BeforeEach
    void setUp() {
        solicitudEjemplo = new Solicitud();
        solicitudEjemplo.setIdSolicitud(1);
        solicitudEjemplo.setMensaje("Quiero adoptar a Luna");
        solicitudEjemplo.setEstado("pendiente");
        solicitudEjemplo.setIdMascota(1);
        solicitudEjemplo.setIdUsuarioAdoptante(1);
    }

    @Test
    void listar_retorna200ConSolicitudes() throws Exception {
        // ARRANGE
        List<Solicitud> listaFalsa = new ArrayList<>();
        listaFalsa.add(solicitudEjemplo);
        when(soliService.listar()).thenReturn(listaFalsa);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("pendiente"))
                .andExpect(jsonPath("$[0].mensaje").value("Quiero adoptar a Luna"));
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        // ARRANGE
        when(soliService.buscarPorId(1)).thenReturn(solicitudEjemplo);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/solicitudes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSolicitud").value(1))
                .andExpect(jsonPath("$.estado").value("pendiente"));
    }

    @Test
    void buscarPorId_retorna400CuandoNoExiste() throws Exception {
        // ARRANGE
        when(soliService.buscarPorId(99)).thenThrow(new RuntimeException("Solicitud no encontrada"));

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/solicitudes/99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearSolicitud_retorna200() throws Exception {
        // ARRANGE
        when(soliService.crearSolicitud(any(SolicitudDTO.class))).thenReturn(solicitudEjemplo);

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mensaje\":\"Quiero adoptar a Luna\",\"idMascota\":1,\"idUsuarioAdoptante\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("pendiente"));
    }

    @Test
    void crearSolicitud_retorna400CuandoFalla() throws Exception {
        // ARRANGE
        when(soliService.crearSolicitud(any(SolicitudDTO.class)))
                .thenThrow(new RuntimeException("Mascota no disponible"));

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mensaje\":\"Quiero adoptar\",\"idMascota\":1,\"idUsuarioAdoptante\":1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cambiarEstado_retorna200() throws Exception {
        // ARRANGE
        solicitudEjemplo.setEstado("aprobada");
        when(soliService.cambiarEstado(1, "aprobada")).thenReturn(solicitudEjemplo);

        // ACT + ASSERT
        mockMvc.perform(put("/api/v1/solicitudes/1/estado")
                .param("estado", "aprobada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("aprobada"));
    }

    @Test
    void cambiarEstado_retorna400CuandoEstadoInvalido() throws Exception {
        // ARRANGE
        when(soliService.cambiarEstado(anyInt(), anyString()))
                .thenThrow(new RuntimeException("Estado inválido"));

        // ACT + ASSERT
        mockMvc.perform(put("/api/v1/solicitudes/1/estado")
                .param("estado", "invalido"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminar_retorna200() throws Exception {
        // ARRANGE
        doNothing().when(soliService).eliminar(1);

        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/solicitudes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_retorna400CuandoNoExiste() throws Exception {
        // ARRANGE
        doThrow(new RuntimeException("Solicitud no encontrada")).when(soliService).eliminar(99);

        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/solicitudes/99"))
                .andExpect(status().isBadRequest());
    }
}
