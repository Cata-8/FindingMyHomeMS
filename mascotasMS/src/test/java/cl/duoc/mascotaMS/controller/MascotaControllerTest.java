package cl.duoc.mascotaMS.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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
    void buscarPorId_retorna200() throws Exception{
        //ARRANGE: el service debe retornar la mascota
        when(service.buscarPorId(1)).thenReturn(mascotaEjemplo);

        //ACT + ASSERT
        mock.perform(get("/api/v1/mascota/1")).andExpect(status().isOk());


    }

    @Test
    void buscarPorId_retorna404() throws Exception{
        //ARRANGE: buscamos una mascota con una id 99 y tira un error
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Mascota no encontrada"));

        //ACT + ASSERT
        mock.perform(get("/api/v1/mascota/99")).andExpect(status().isNotFound());// o sea un codigo HTTPS 404

    }
    
}
