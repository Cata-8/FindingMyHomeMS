package cl.duoc.fichaSaludMS.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.fichaSaludMS.model.FichaSalud;
import cl.duoc.fichaSaludMS.service.FichaSaludService;

@WebMvcTest(FichaSaludController.class)
public class FichaSaludControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FichaSaludService service;

    private FichaSalud fichaEjemplo;

    @BeforeEach
    void setUp() {
        fichaEjemplo = new FichaSalud();
        fichaEjemplo.setId(1);
        fichaEjemplo.setEnfermedades("ninguna");
        fichaEjemplo.setEstadoReproductivo("esterilizado");
        fichaEjemplo.setMicroship(false);
        fichaEjemplo.setDesparasitado(true);
        fichaEjemplo.setIdMascota(1);
        fichaEjemplo.setRegistroVacunas(new ArrayList<>());
    }

    @Test
    void listar_retorna200ConFichas() throws Exception {
        // ARRANGE
        List<FichaSalud> listaFalsa = new ArrayList<>();
        listaFalsa.add(fichaEjemplo);
        when(service.listar()).thenReturn(listaFalsa);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/fichaSalud"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].enfermedades").value("ninguna"))
                .andExpect(jsonPath("$[0].estadoReproductivo").value("esterilizado"));
    }

    @Test
    void listar_retorna204SinFichas() throws Exception {
        // ARRANGE
        when(service.listar()).thenReturn(new ArrayList<>());

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/fichaSalud"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscar_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(fichaEjemplo);

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/fichaSalud/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.enfermedades").value("ninguna"));
    }

    @Test
    void buscar_retorna404CuandoNoExiste() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Ficha no encontrada"));

        // ACT + ASSERT
        mockMvc.perform(get("/api/v1/fichaSalud/99"))
                .andExpect(status().isNotFound());
    }
}
