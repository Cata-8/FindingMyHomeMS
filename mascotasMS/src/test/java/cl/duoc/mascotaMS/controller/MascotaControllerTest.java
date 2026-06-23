package cl.duoc.mascotaMS.controller;

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
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.mascotaMS.model.Mascota;
import cl.duoc.mascotaMS.service.MascotaService;

@WebMvcTest(MascotaController.class)//levanta solo la capa web, no la bd
public class MascotaControllerTest {

    @Autowired
    private MockMvc mock; //mock que simula las peticiones HTTP

    @MockitoBean
    private MascotaService service; //service falso
    
    private Mascota mascotaEjemplo;

    @BeforeEach
    void setUp(){
        mascotaEjemplo = new Mascota();
        mascotaEjemplo.setId(1);
        mascotaEjemplo.setNombre("Leo");
        mascotaEjemplo.setTipo("Perro");
        mascotaEjemplo.setEdad(3);
        mascotaEjemplo.setDescripcion("Tranquilo y cariñoso");
        mascotaEjemplo.setEstado("Disponible");
        mascotaEjemplo.setUbicacion("Talca");
        mascotaEjemplo.setFecha_publicacion(new Date());
    }

    @Test
    void listar_retorna200ConMascotas() throws Exception {
        // ARRANGE: creamos la lista falsa
        List<Mascota> listaFalsa = new ArrayList<>();
        listaFalsa.add(mascotaEjemplo);

        // le decimos al servicio falso que devuelva esa lista
        when(service.listar()).thenReturn(listaFalsa);

        // ACT + ASSERT: simulamos el GET y verificamos la respuesta
        mock.perform(get("/api/v1/mascota"))
            .andExpect(status().isOk())      
            .andExpect(jsonPath("$[0].id").value(1))                       
            .andExpect(jsonPath("$[0].nombre").value("Leo"))
            .andExpect(jsonPath("$[0].estado").value("Disponible"));; 
    }

    @Test
    void listar_retorna204SinDoctores() throws Exception {
        // ARRANGE: lista vacía
        List<Mascota> listaVacia = new ArrayList<>();
        when(service.listar()).thenReturn(listaVacia);

        // ACT + ASSERT: verificamos que retorna 204 sin contenido
        mock.perform(get("/api/v1/mascota"))
            .andExpect(status().isNoContent()); // código HTTP 204
    }

    @Test
    void buscarPorId_retorna200() throws Exception{
        //ARRANGE: el service debe retornar la mascota
        when(service.buscarPorId(1)).thenReturn(mascotaEjemplo);

        //ACT + ASSERT
        mock.perform(get("/api/v1/mascota/1")).andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Leo"))
            .andExpect(jsonPath("$.tipo").value("Perro"));;
    }

    @Test
    void buscarPorId_retorna404() throws Exception{
        //ARRANGE: buscamos una mascota con una id 99 y tira un error
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Mascota no encontrada"));

        //ACT + ASSERT
        mock.perform(get("/api/v1/mascota/99")).andExpect(status().isNotFound());// o sea un codigo HTTPS 404

    }

    @Test
    void guardar_retorna200() throws Exception {
        // ARRANGE
        when(service.guardar(any(Mascota.class))).thenReturn(mascotaEjemplo);

        // ACT + ASSERT
        mock.perform(post("/api/v1/mascota")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"nombre\":\"Leo\",\"tipo\": \"Perro\",\"edad\": 3,\"descripcion\": \"Tranquilo y cariñoso\",\"estado\": \"Disponible\",\"ubicacion\": \"Talca\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Leo"));
    }

    @Test
    void buscarPorEstado_retorna200ConMascotas() throws Exception {
        // ARRANGE
        List<Mascota> listaFalsa = new ArrayList<>();
        listaFalsa.add(mascotaEjemplo);
        when(service.buscarPorEstado("Disponible")).thenReturn(listaFalsa);
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/mascota/estado/Disponible"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].estado").value("Disponible"))
            .andExpect(jsonPath("$[0].nombre").value("Leo"));
    }

    @Test
    void buscarPorEstado_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarPorEstado("invalido")).thenThrow(new RuntimeException("Estado no válido"));
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/mascota/estado/invalido"))
            .andExpect(status().isNotFound());                               
    }
    
    @Test
    void eliminar_retorna204() throws Exception {
        // ARRANGE: el servicio no hace nada (método void)
        doNothing().when(service).eliminar(1);

        // ACT + ASSERT
        mock.perform(delete("/api/v1/mascota/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {
        // ARRANGE: el servicio lanza excepción
        doThrow(new RuntimeException("Mascota no existe")).when(service).eliminar(99);

        // ACT + ASSERT
        mock.perform(delete("/api/v1/mascota/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void obtenerMascotaDTO_retorna200() throws Exception {
        // ARRANGE: el controller llama buscarPorId y construye el DTO internamente
        when(service.buscarPorId(1)).thenReturn(mascotaEjemplo);
 
        // ACT + ASSERT
        mock.perform(get("/api/v1/mascota/dto/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Leo"))
            .andExpect(jsonPath("$.tipo").value("Perro"))
            .andExpect(jsonPath("$.estado").value("Disponible"));
    }
 
    @Test
    void obtenerMascotaDTO_retorna404() throws Exception {
        // ARRANGE: el service lanza excepcion cuando no encuentra la mascota
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Mascota no encontrada"));
        // ACT + ASSERT
        mock.perform(get("/api/v1/mascota/dto/99"))
            .andExpect(status().isNotFound());                             
    }

}
