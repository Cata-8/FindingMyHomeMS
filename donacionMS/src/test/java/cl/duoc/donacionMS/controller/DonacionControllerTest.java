package cl.duoc.donacionMS.controller;

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
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.donacionMS.model.Donacion;
import cl.duoc.donacionMS.service.DonacionService;

import org.springframework.http.MediaType;

@WebMvcTest(DonacionController.class)
public class DonacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DonacionService service;

    private Donacion donacionEjemplo;
    
    @BeforeEach
    void setUp(){
        donacionEjemplo = new Donacion();
        donacionEjemplo.setId(1);
        donacionEjemplo.setMonto(20000);
        donacionEjemplo.setFecha(new Date());
        donacionEjemplo.setMetodoDePago("Tarjeta");
        donacionEjemplo.setIdUsuario(2);
    }

    //listar_retorna200ConDoctores()

    @Test
    void listar_retorna204SinDocnaciones() throws Exception {
        // ARRANGE: lista vacía
        List<Donacion> listaVacia = new ArrayList<>();
        when(service.listar()).thenReturn(listaVacia);

        // ACT + ASSERT: verificamos que retorna 204 sin contenido
        mockMvc.perform(get("/api/v1/donaciones"))
                .andExpect(status().isNoContent()); // código HTTP 204
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        // ARRANGE: el servicio devuelve el doctor
        when(service.buscarPorId(1)).thenReturn(donacionEjemplo);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/donaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        // ARRANGE: el servicio lanza excepción
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Donación no encontrada"));

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/donaciones/99"))
                .andExpect(status().isNotFound()); // código HTTP 404
    }

    @Test
    void guardar_retorna200() throws Exception {
        // ARRANGE
        when(service.guardar(any(Donacion.class))).thenReturn(donacionEjemplo);

        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/donaciones")
                    .contentType(MediaType.APPLICATION_JSON)
                    //.content("{\"id\":1,\"monto\":\"20000\",\"rut\":\"12345678-9\"}")
                )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void eliminar_retorna204() throws Exception {
        // ARRANGE: el servicio no hace nada (método void)
        doNothing().when(service).eliminar(1);

        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/donaciones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {
        // ARRANGE: el servicio lanza excepción
        doThrow(new RuntimeException("Donación no existe")).when(service).eliminar(99);

        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/donaciones/99"))
                .andExpect(status().isNotFound());
    }
    
}
