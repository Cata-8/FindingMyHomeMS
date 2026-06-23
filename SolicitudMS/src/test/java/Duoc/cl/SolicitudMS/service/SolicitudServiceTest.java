package Duoc.cl.SolicitudMS.service;

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

import Duoc.cl.SolicitudMS.client.MascotaClient;
import Duoc.cl.SolicitudMS.client.UsuarioClient;
import Duoc.cl.SolicitudMS.dto.MascotaDTO;
import Duoc.cl.SolicitudMS.dto.SolicitudDTO;
import Duoc.cl.SolicitudMS.model.Solicitud;
import Duoc.cl.SolicitudMS.repository.SolicitudRepository;

@ExtendWith(MockitoExtension.class)
public class SolicitudServiceTest {

    @Mock
    private SolicitudRepository solicitudRepo;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private MascotaClient mascotaClient;

    @InjectMocks
    private SolicitudService solicitudService;

    private Solicitud solicitudEjemplo;
    private SolicitudDTO solicitudDTO;
    private MascotaDTO mascotaDTO;

    @BeforeEach
    void setUp() {
        solicitudEjemplo = new Solicitud();
        solicitudEjemplo.setIdSolicitud(1);
        solicitudEjemplo.setMensaje("Quiero adoptar a Luna");
        solicitudEjemplo.setEstado("pendiente");
        solicitudEjemplo.setIdMascota(1);
        solicitudEjemplo.setIdUsuarioAdoptante(1);

        solicitudDTO = new SolicitudDTO();
        solicitudDTO.setMensaje("Quiero adoptar a Luna");
        solicitudDTO.setIdMascota(1);
        solicitudDTO.setIdUsuarioAdoptante(1);

        mascotaDTO = new MascotaDTO();
        mascotaDTO.setId(1);
        mascotaDTO.setNombre("Luna");
        mascotaDTO.setTipo("Perro");
        mascotaDTO.setEstado("disponible");
    }

    @Test
    void listar_retornaListaConSolicitudes() {
        // ARRANGE
        List<Solicitud> listaFalsa = new ArrayList<>();
        listaFalsa.add(solicitudEjemplo);
        when(solicitudRepo.findAll()).thenReturn(listaFalsa);

        // ACT
        List<Solicitud> resultado = solicitudService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Quiero adoptar a Luna", resultado.get(0).getMensaje());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(solicitudRepo.findById(1)).thenReturn(Optional.of(solicitudEjemplo));

        // ACT
        Solicitud resultado = solicitudService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getIdSolicitud());
        assertEquals("pendiente", resultado.getEstado());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(solicitudRepo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            solicitudService.buscarPorId(99);
        });

        assertEquals("Solicitud no encontrada", ex.getMessage());
    }

    @Test
    void crearSolicitud_exitoso() {
        // ARRANGE
        when(usuarioClient.buscarUsuario(1)).thenReturn(new Object());
        when(mascotaClient.buscarMascota(1)).thenReturn(mascotaDTO);
        when(solicitudRepo.save(any(Solicitud.class))).thenReturn(solicitudEjemplo);

        // ACT
        Solicitud resultado = solicitudService.crearSolicitud(solicitudDTO);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("pendiente", resultado.getEstado());
        verify(solicitudRepo, times(1)).save(any(Solicitud.class));
    }

    @Test
    void crearSolicitud_usuarioNoExiste() {
        // ARRANGE
        doThrow(new RuntimeException()).when(usuarioClient).buscarUsuario(1);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            solicitudService.crearSolicitud(solicitudDTO);
        });

        assertEquals("Usuario no existe", ex.getMessage());
    }

    @Test
    void crearSolicitud_mascotaNoExiste() {
        // ARRANGE
        when(usuarioClient.buscarUsuario(1)).thenReturn(new Object());
        doThrow(new RuntimeException()).when(mascotaClient).buscarMascota(1);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            solicitudService.crearSolicitud(solicitudDTO);
        });

        assertEquals("Mascota no existe", ex.getMessage());
    }

    @Test
    void crearSolicitud_mascotaNoDisponible() {
        // ARRANGE
        mascotaDTO.setEstado("adoptado");
        when(usuarioClient.buscarUsuario(1)).thenReturn(new Object());
        when(mascotaClient.buscarMascota(1)).thenReturn(mascotaDTO);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            solicitudService.crearSolicitud(solicitudDTO);
        });

        assertEquals("Mascota no disponible", ex.getMessage());
    }

    @Test
    void cambiarEstado_exitoso() {
        // ARRANGE
        when(solicitudRepo.findById(1)).thenReturn(Optional.of(solicitudEjemplo));
        solicitudEjemplo.setEstado("aprobada");
        when(solicitudRepo.save(any(Solicitud.class))).thenReturn(solicitudEjemplo);

        // ACT
        Solicitud resultado = solicitudService.cambiarEstado(1, "aprobada");

        // ASSERT
        assertEquals("aprobada", resultado.getEstado());
        verify(solicitudRepo, times(1)).save(any(Solicitud.class));
    }

    @Test
    void cambiarEstado_estadoInvalido() {
        // ARRANGE
        when(solicitudRepo.findById(1)).thenReturn(Optional.of(solicitudEjemplo));

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            solicitudService.cambiarEstado(1, "invalido");
        });

        assertEquals("Estado inválido", ex.getMessage());
    }

    @Test
    void cambiarEstado_solicitudNoEncontrada() {
        // ARRANGE
        when(solicitudRepo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            solicitudService.cambiarEstado(99, "aprobada");
        });

        assertEquals("Solicitud no encontrada", ex.getMessage());
    }

    @Test
    void eliminar_exitoso() {
        // ARRANGE
        when(solicitudRepo.findById(1)).thenReturn(Optional.of(solicitudEjemplo));

        // ACT + ASSERT
        assertDoesNotThrow(() -> solicitudService.eliminar(1));
        verify(solicitudRepo, times(1)).delete(solicitudEjemplo);
    }

    @Test
    void eliminar_noEncontrado() {
        // ARRANGE
        when(solicitudRepo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            solicitudService.eliminar(99);
        });

        assertEquals("Solicitud no encontrada", ex.getMessage());
        verify(solicitudRepo, never()).delete(any(Solicitud.class));
    }
}
