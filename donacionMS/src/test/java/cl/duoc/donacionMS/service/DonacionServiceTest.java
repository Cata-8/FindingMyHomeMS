package cl.duoc.donacionMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.donacionMS.model.Donacion;
import cl.duoc.donacionMS.repository.DonacionRepository;

@ExtendWith(MockitoExtension.class)
public class DonacionServiceTest {

    @Mock
    private DonacionRepository donacionRepository;

    @InjectMocks
    private DonacionService donacionService;

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
    void listar_retornaListaConDonaciones(){
        // ARRANGE: creamos la lista manualmente
        List<Donacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(donacionEjemplo);

        // le decimos al repositorio falso que devuelva esa lista cuando se llame findAll()
        when(donacionRepository.findAll()).thenReturn(listaFalsa);

        // ACT: llamamos al método real del servicio
        List<Donacion> resultado = donacionService.listar();

        // ASSERT: verificamos el resultado
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE: el repositorio devuelve un Optional con el doctor
        Optional<Donacion> optionalDonacion = Optional.of(donacionEjemplo);
        when(donacionRepository.findById(1)).thenReturn(optionalDonacion);

        // ACT: llamamos al método real
        Donacion resultado = donacionService.buscarPorId(1);

        // ASSERT: verificamos que es el doctor correcto
        assertEquals(1, resultado.getId());
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE: el repositorio devuelve un Optional vacío
        Optional<Donacion> optionalVacio = Optional.empty();
        when(donacionRepository.findById(99)).thenReturn(optionalVacio);

        // ACT + ASSERT: verificamos que lanza la excepción correcta
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            donacionService.buscarPorId(99);
        });

        assertEquals("Donación no encontrado", ex.getMessage());
    }

    @Test
    void guardar_retornaDonacionGuardado() {
        // ARRANGE: el repositorio devuelve el doctor al guardarlo
        when(donacionRepository.save(donacionEjemplo)).thenReturn(donacionEjemplo);

        // ACT
        Donacion resultado = donacionService.guardar(donacionEjemplo);

            // ASSERT
        assertEquals(1, resultado.getId());
        verify(donacionRepository, times(1)).save(donacionEjemplo); // verificamos que save fue llamado
    }

    @Test
    void eliminar_exitoso() {
        // ARRANGE: el doctor existe
        when(donacionRepository.existsById(1)).thenReturn(true);

        // ACT + ASSERT: no debe lanzar excepción
        assertDoesNotThrow(() -> donacionService.eliminar(1));

        // verificamos que deleteById fue llamado exactamente una vez
        verify(donacionRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        // ARRANGE: el doctor no existe
        when(donacionRepository.existsById(99)).thenReturn(false);

        // ACT + ASSERT: debe lanzar excepción
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            donacionService.eliminar(99);
        });

        assertEquals("Doctor no existe", ex.getMessage());

        // verificamos que deleteById NUNCA fue llamado
        verify(donacionRepository, never()).deleteById(99);
    }

}
