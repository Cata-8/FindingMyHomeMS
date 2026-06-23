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

import cl.duoc.donacionMS.dto.DonacionDetalleDTO;
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

    @Test
    void listar_retorna200ConDonaciones() throws Exception {
        // ARRANGE
        List<Donacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(donacionEjemplo);
        when(service.listar()).thenReturn(listaFalsa);
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/donaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].monto").value(20000))
                .andExpect(jsonPath("$[0].metodoDePago").value("Tarjeta"));
    }
 
    @Test
    void listar_retorna204SinDonaciones() throws Exception {
        // ARRANGE
        when(service.listar()).thenReturn(new ArrayList<>());
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/donaciones"))
                .andExpect(status().isNoContent());
    }
 
    @Test
    void buscarPorId_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(donacionEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/donaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(20000));
    }
 
    @Test
    void buscarPorId_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Donación no encontrada"));
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/donaciones/99"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void detalle_retorna200() throws Exception {
        // ARRANGE
        DonacionDetalleDTO dto = new DonacionDetalleDTO(1, new Date(), 2);
        when(service.buscarPorId(1)).thenReturn(donacionEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/donaciones/1/detalle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.idUsuario").value(2));
    }
 
    @Test
    void detalle_retorna404CuandoNoExiste() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Donación no encontrada"));
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/donaciones/99/detalle"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void guardar_retorna200() throws Exception {
        // ARRANGE
        when(service.guardar(any(Donacion.class))).thenReturn(donacionEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/donaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"monto\":20000,\"metodoDePago\":\"Tarjeta\",\"idUsuario\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(20000));
    }
 
    @Test
    void guardar_retorna404CuandoUsuarioNoExiste() throws Exception {
        // ARRANGE
        when(service.guardar(any(Donacion.class))).thenThrow(new RuntimeException("Usuario no existe"));
 
        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/donaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"monto\":20000,\"metodoDePago\":\"Tarjeta\",\"idUsuario\":99}"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void eliminar_retorna204() throws Exception {
        // ARRANGE
        doNothing().when(service).eliminar(1);
 
        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/donaciones/1"))
                .andExpect(status().isNoContent());
    }
 
    @Test
    void eliminar_retorna404CuandoNoExiste() throws Exception {
        // ARRANGE
        doThrow(new RuntimeException("Donacion no existe")).when(service).eliminar(99);
 
        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/donaciones/99"))
                .andExpect(status().isNotFound());
    }
    
}
