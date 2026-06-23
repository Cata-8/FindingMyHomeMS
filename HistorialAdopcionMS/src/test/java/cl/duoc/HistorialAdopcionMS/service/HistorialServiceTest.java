package cl.duoc.HistorialAdopcionMS.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.Duoc.HistorialAdopcionMS.client.MascotaClient;
import cl.Duoc.HistorialAdopcionMS.client.UsuarioClient;
import cl.Duoc.HistorialAdopcionMS.dto.HistorialDTO;
import cl.Duoc.HistorialAdopcionMS.dto.MascotaDTO;
import cl.Duoc.HistorialAdopcionMS.dto.UsuarioDTO;
import cl.Duoc.HistorialAdopcionMS.model.HistorialAdopcion;
import cl.Duoc.HistorialAdopcionMS.repository.HistorialRepository;
import cl.Duoc.HistorialAdopcionMS.service.HistorialService;

@ExtendWith(MockitoExtension.class)
public class HistorialServiceTest {

    @Mock
    private HistorialRepository histoRepo;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private MascotaClient mascotaClient;

    @InjectMocks
    private HistorialService historialService;

    private HistorialAdopcion historialEjemplo;
    private HistorialDTO historialDTO;
    private MascotaDTO mascotaDTO;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        historialEjemplo = new HistorialAdopcion();
        historialEjemplo.setIdHistorial(1);
        historialEjemplo.setIdUsuario(1);
        historialEjemplo.setIdMascota(2);

        historialDTO = new HistorialDTO();
        historialDTO.setIdUsuario(1);
        historialDTO.setIdMascota(2);

        mascotaDTO = new MascotaDTO();
        mascotaDTO.setId(2);
        mascotaDTO.setNombre("Milo");
        mascotaDTO.setTipo("Gato");
        mascotaDTO.setEstado("adoptado");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(1);
        usuarioDTO.setNombre("Carlos");
        usuarioDTO.setApellido("González");
    }

    @Test
    void listar_retornaListaConHistoriales() {
        // ARRANGE
        List<HistorialAdopcion> listaFalsa = new ArrayList<>();
        listaFalsa.add(historialEjemplo);
        when(histoRepo.findAll()).thenReturn(listaFalsa);

        // ACT
        List<HistorialAdopcion> resultado = historialService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdUsuario());
        assertEquals(2, resultado.get(0).getIdMascota());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(histoRepo.findById(1)).thenReturn(Optional.of(historialEjemplo));

        // ACT
        HistorialAdopcion resultado = historialService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getIdHistorial());
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(histoRepo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            historialService.buscarPorId(99);
        });

        assertEquals("Historial no encontrado", ex.getMessage());
    }

    @Test
    void crearHistorial_exitoso() {
        // ARRANGE
        when(usuarioClient.buscarUsuario(1)).thenReturn(usuarioDTO);
        when(mascotaClient.buscarMascota(2)).thenReturn(mascotaDTO);
        when(histoRepo.save(any(HistorialAdopcion.class))).thenReturn(historialEjemplo);

        // ACT
        HistorialAdopcion resultado = historialService.crearHistorial(historialDTO);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdUsuario());
        assertEquals(2, resultado.getIdMascota());
        verify(histoRepo, times(1)).save(any(HistorialAdopcion.class));
    }

    @Test
    void crearHistorial_usuarioNoExiste() {
        // ARRANGE
        doThrow(new RuntimeException()).when(usuarioClient).buscarUsuario(1);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            historialService.crearHistorial(historialDTO);
        });

        assertEquals("Usuario no existe", ex.getMessage());
    }

    @Test
    void crearHistorial_mascotaNoExiste() {
        // ARRANGE
        when(usuarioClient.buscarUsuario(1)).thenReturn(usuarioDTO);
        doThrow(new RuntimeException()).when(mascotaClient).buscarMascota(2);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            historialService.crearHistorial(historialDTO);
        });

        assertEquals("Mascota no existe", ex.getMessage());
    }

    @Test
    void crearHistorial_mascotaNoAdoptada() {
        // ARRANGE
        mascotaDTO.setEstado("disponible");
        when(usuarioClient.buscarUsuario(1)).thenReturn(usuarioDTO);
        when(mascotaClient.buscarMascota(2)).thenReturn(mascotaDTO);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            historialService.crearHistorial(historialDTO);
        });

        assertEquals("La mascota aún no está adoptada", ex.getMessage());
    }

    @Test
    void eliminar_exitoso() {
        // ARRANGE
        when(histoRepo.findById(1)).thenReturn(Optional.of(historialEjemplo));

        // ACT + ASSERT
        assertDoesNotThrow(() -> historialService.eliminar(1));
        verify(histoRepo, times(1)).delete(historialEjemplo);
    }

    @Test
    void eliminar_noEncontrado() {
        // ARRANGE
        when(histoRepo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            historialService.eliminar(99);
        });

        assertEquals("Historial no encontrado", ex.getMessage());
        verify(histoRepo, never()).delete(any(HistorialAdopcion.class));
    }
}
