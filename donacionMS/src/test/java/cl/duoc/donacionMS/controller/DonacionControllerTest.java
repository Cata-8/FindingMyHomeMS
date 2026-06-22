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
import static org.mockito.ArgumentMatchers.any;

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

    
}
