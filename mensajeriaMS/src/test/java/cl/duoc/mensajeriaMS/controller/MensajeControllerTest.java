package cl.duoc.mensajeriaMS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.mensajeriaMS.dto.MensajeDTO;
import cl.duoc.mensajeriaMS.model.Mensaje;
import cl.duoc.mensajeriaMS.service.MensajeService;

@WebMvcTest(MensajeController.class)
public class MensajeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MensajeService mensajeService;

    private Mensaje mensajeEjemplo;

    @BeforeEach
    void setUp() {
        mensajeEjemplo = new Mensaje();
        mensajeEjemplo.setId(1);
        mensajeEjemplo.setIdRemitente(1);
        mensajeEjemplo.setIdDestinatario(2);
        mensajeEjemplo.setContenido("Hola, quiero adoptar la mascota");
        mensajeEjemplo.setFechaEnvio(LocalDate.now());
        mensajeEjemplo.setLeido(false);
    }

    @Test
    void listar_retorna200ConMensajes() throws Exception {
        // ARRANGE
        List<Mensaje> listaFalsa = new ArrayList<>();
        listaFalsa.add(mensajeEjemplo);
        when(mensajeService.listar()).thenReturn(listaFalsa);
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/mensajes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].contenido").value("Hola, quiero adoptar la mascota"))
                .andExpect(jsonPath("$[0].leido").value(false));
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        // ARRANGE
        when(mensajeService.buscarPorId(1)).thenReturn(mensajeEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/mensajes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.idRemitente").value(1))
                .andExpect(jsonPath("$.idDestinatario").value(2));
    }

    @Test
    void buscarPorId_retorna400() throws Exception {
        // ARRANGE
        when(mensajeService.buscarPorId(99))
                .thenThrow(new RuntimeException("Mensaje no encontrado"));
 
        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/mensajes/99"))
                .andExpect(status().isBadRequest());                                   
    }

    @Test
    void crear_retorna200() throws Exception {
        // ARRANGE
        when(mensajeService.crearMensaje(any(MensajeDTO.class))).thenReturn(mensajeEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/mensajes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idRemitente\":1,\"idDestinatario\":2,\"contenido\":\"Hola, quiero adoptar la mascota\",\"leido\":false}"))
                .andExpect(status().isOk())                                               // 200
                .andExpect(jsonPath("$.contenido").value("Hola, quiero adoptar la mascota"))
                .andExpect(jsonPath("$.idRemitente").value(1))
                .andExpect(jsonPath("$.idDestinatario").value(2));
    }

    @Test
    void crear_retorna400() throws Exception {
        // ARRANGE
        when(mensajeService.crearMensaje(any(MensajeDTO.class)))
                .thenThrow(new RuntimeException("Usuario no existe"));
 
        // ACT + ASSERT
        mockMvc.perform(post("/api/v1/mensajes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idRemitente\":99,\"idDestinatario\":2,\"contenido\":\"Hola\",\"leido\":false}"))
                .andExpect(status().isBadRequest());                                   
    }

    @Test
    void marcarLeido_retorna200() throws Exception {
        // ARRANGE
        mensajeEjemplo.setLeido(true);
        when(mensajeService.marcarComoLeido(1)).thenReturn(mensajeEjemplo);
 
        // ACT + ASSERT
        mockMvc.perform(put("/api/v1/mensajes/1/leer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leido").value(true));
    }
 
    @Test
    void marcarLeido_retorna400() throws Exception {
        // ARRANGE
        when(mensajeService.marcarComoLeido(99))
                .thenThrow(new RuntimeException("Mensaje no encontrado"));
 
        // ACT + ASSERT
        mockMvc.perform(put("/api/v1/mensajes/99/leer"))
                .andExpect(status().isBadRequest());                                      
    }

    @Test
    void eliminar_retorna200() throws Exception {
        // ARRANGE: el controller retorna ok("Mensaje eliminado") cuando no hay excepcion
        doNothing().when(mensajeService).eliminar(1);
 
        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/mensajes/1"))
                .andExpect(status().isOk());
    }
 
    @Test
    void eliminar_retorna400() throws Exception {
        // ARRANGE
        doThrow(new RuntimeException("Mensaje no encontrado")).when(mensajeService).eliminar(99);
 
        // ACT + ASSERT
        mockMvc.perform(delete("/api/v1/mensajes/99"))
                .andExpect(status().isBadRequest());                                      
    }
}
