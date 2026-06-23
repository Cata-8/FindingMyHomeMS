package cl.duoc.HistorialAdopcionMS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import cl.Duoc.HistorialAdopcionMS.controller.HistorialController;
import cl.Duoc.HistorialAdopcionMS.dto.HistorialDTO;
import cl.Duoc.HistorialAdopcionMS.model.HistorialAdopcion;
import cl.Duoc.HistorialAdopcionMS.service.HistorialService;

@WebMvcTest(HistorialController.class)
public class HistorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HistorialService historialService;

    private HistorialAdopcion historialEjemplo;

    @BeforeEach
    void setUp() {
        historialEjemplo = new HistorialAdopcion();
        historialEjemplo.setIdHistorial(1);
        historialEjemplo.setIdUsuario(1);
        historialEjemplo.setIdMascota(2);
    }

    @Test
    void listar_retorna200ConHistoriales() throws Exception {
        // ARRANGE
        List<HistorialAdopcion> listaFalsa = new ArrayList<>();
        listaFalsa.add(historialEjemplo);
        when(historialService.listar()).thenReturn(listaFalsa);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/historial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(1))
                .andExpect(jsonPath("$[0].idMascota").value(2));
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        // ARRANGE
        when(historialService.buscarPorId(1)).thenReturn(historialEjemplo);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/historial/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idHistorial").value(1))
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    void buscarPorId_retorna400CuandoNoExiste() throws Exception {
        // ARRANGE
        when(historialService.buscarPorId(99)).thenThrow(new RuntimeException("Historial no encontrado"));

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/historial/99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crear_retorna200() throws Exception {
        // ARRANGE
        when(historialService.crearHistorial(any(HistorialDTO.class))).thenReturn(historialEjemplo);

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/historial")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\":1,\"idMascota\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.idMascota").value(2));
    }

    @Test
    void crear_retorna400CuandoFalla() throws Exception {
        // ARRANGE
        when(historialService.crearHistorial(any(HistorialDTO.class)))
                .thenThrow(new RuntimeException("La mascota aún no está adoptada"));

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/historial")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\":1,\"idMascota\":2}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminar_retorna200() throws Exception {
        // ARRANGE
        doNothing().when(historialService).eliminar(1);

        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/historial/1"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_retorna400CuandoNoExiste() throws Exception {
        // ARRANGE
        doThrow(new RuntimeException("Historial no encontrado")).when(historialService).eliminar(99);

        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/historial/99"))
                .andExpect(status().isBadRequest());
    }
}
