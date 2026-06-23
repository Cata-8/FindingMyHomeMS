package cl.duoc.publicacionMS.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.publicacionMS.model.Publicacion;
import cl.duoc.publicacionMS.service.PublicacionService;

@WebMvcTest(PublicacionController.class)
public class PublicacionControllerTest {
    @Autowired
    private MockMvc mockMvc;
 
    @MockitoBean
    private PublicacionService service;
 
    private Publicacion publicacionEjemplo;
 
    @BeforeEach
    void setUp() {
        publicacionEjemplo = new Publicacion();
        publicacionEjemplo.setId(1);
        publicacionEjemplo.setTitulo("Luna busca hogar");
        publicacionEjemplo.setDescripcion("Perrita juguetona busca familia amorosa");
        publicacionEjemplo.setFechaPublicacion(new Date());
        publicacionEjemplo.setEstado("Activa");
        publicacionEjemplo.setIdMascota(1);
    }
 
    @Test
    void listar_retorna200ConPublicaciones() throws Exception {
        // ARRANGE
        List<Publicacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(publicacionEjemplo);
        when(service.listar()).thenReturn(listaFalsa);
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/publicacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Luna busca hogar"))
                .andExpect(jsonPath("$[0].estado").value("Activa"));
    }
 
    @Test
    void listar_retorna204SinPublicaciones() throws Exception {
        // ARRANGE
        when(service.listar()).thenReturn(new ArrayList<>());
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/publicacion"))
                .andExpect(status().isNoContent());
    }
 
    @Test
    void buscar_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(publicacionEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/publicacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Luna busca hogar"));
    }
 
    @Test
    void buscar_retorna404CuandoNoExiste() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Publicación no encontrada"));
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/publicacion/99"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void detalle_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(publicacionEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/publicacion/1/detalle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Luna busca hogar"))
                .andExpect(jsonPath("$.idMascota").value(1));
    }
 
    @Test
    void detalle_retorna404CuandoNoExiste() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Publicación no encontrada"));
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/publicacion/99/detalle"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void guardar_retorna200() throws Exception {
        // ARRANGE
        when(service.guardar(any(Publicacion.class))).thenReturn(publicacionEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/publicacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Luna busca hogar\",\"descripcion\":\"Perrita juguetona\","
                        + "\"estado\":\"Activa\",\"idMascota\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Luna busca hogar"));
    }
 
    @Test
    void guardar_retorna404CuandoMascotaNoExiste() throws Exception {
        // ARRANGE
        when(service.guardar(any(Publicacion.class))).thenThrow(new RuntimeException("mascota no existe"));
 
        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/publicacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Publicacion test\",\"descripcion\":\"desc\","
                        + "\"estado\":\"Activa\",\"idMascota\":99}"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void eliminar_retorna204() throws Exception {
        // ARRANGE
        doNothing().when(service).eliminar(1);
 
        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/publicacion/1"))
                .andExpect(status().isNoContent());
    }
 
    @Test
    void eliminar_retorna404CuandoNoExiste() throws Exception {
        // ARRANGE
        doThrow(new RuntimeException("Publicacion no existe")).when(service).eliminar(99);
 
        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/publicacion/99"))
                .andExpect(status().isNotFound());
    }
}
