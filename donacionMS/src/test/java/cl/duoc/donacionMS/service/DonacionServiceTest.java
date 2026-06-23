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

import cl.duoc.donacionMS.client.UsuarioClient;
import cl.duoc.donacionMS.dto.DonacionDetalleDTO;
import cl.duoc.donacionMS.dto.UsuarioDTO;
import cl.duoc.donacionMS.model.Donacion;
import cl.duoc.donacionMS.repository.DonacionRepository;

@ExtendWith(MockitoExtension.class)
public class DonacionServiceTest {

    @Mock
    private DonacionRepository donacionRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private DonacionService donacionService;

    private Donacion donacionEjemplo;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        donacionEjemplo = new Donacion();
        donacionEjemplo.setId(1);
        donacionEjemplo.setMonto(20000);
        donacionEjemplo.setFecha(new Date());
        donacionEjemplo.setMetodoDePago("Tarjeta");
        donacionEjemplo.setIdUsuario(2);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(2);
    }

    @Test
    void listar_retornaListaConDonaciones() {
        // ARRANGE
        List<Donacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(donacionEjemplo);
        when(donacionRepository.findAll()).thenReturn(listaFalsa);

        // ACT
        List<Donacion> resultado = donacionService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(20000, resultado.get(0).getMonto());
        assertEquals("Tarjeta", resultado.get(0).getMetodoDePago());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(donacionRepository.findById(1)).thenReturn(Optional.of(donacionEjemplo));

        // ACT
        Donacion resultado = donacionService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals(20000, resultado.getMonto());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(donacionRepository.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            donacionService.buscarPorId(99);
        });

        assertEquals("Donación no encontrada", ex.getMessage());
    }

    @Test
    void guardar_exitoso() {
        // ARRANGE: usuario existe
        when(usuarioClient.obtenerUsuario(2)).thenReturn(usuarioDTO);
        when(donacionRepository.save(donacionEjemplo)).thenReturn(donacionEjemplo);

        // ACT
        Donacion resultado = donacionService.guardar(donacionEjemplo);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals(20000, resultado.getMonto());
        verify(donacionRepository, times(1)).save(donacionEjemplo);
    }

    @Test
    void guardar_usuarioNoExiste() {
        // ARRANGE: el cliente retorna null
        when(usuarioClient.obtenerUsuario(2)).thenReturn(null);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            donacionService.guardar(donacionEjemplo);
        });

        assertEquals("Usuario no existe", ex.getMessage());
        verify(donacionRepository, never()).save(any(Donacion.class));
    }

    @Test
    void obtenerDetalle_exitoso() {
        // ARRANGE
        when(donacionRepository.findById(1)).thenReturn(Optional.of(donacionEjemplo));
        when(usuarioClient.obtenerUsuario(2)).thenReturn(usuarioDTO);

        // ACT
        DonacionDetalleDTO resultado = donacionService.obtenerDetalle(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(2, resultado.getIdUsuario());
    }


    @Test
    void eliminar_exitoso() {
        // ARRANGE
        when(donacionRepository.existsById(1)).thenReturn(true);

        // ACT + ASSERT
        assertDoesNotThrow(() -> donacionService.eliminar(1));
        verify(donacionRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        // ARRANGE
        when(donacionRepository.existsById(99)).thenReturn(false);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            donacionService.eliminar(99);
        });

        assertEquals("Donacion no existe", ex.getMessage());
        verify(donacionRepository, never()).deleteById(99);
    }
}