package cl.duoc.notificacionMS.service;

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

import cl.duoc.notificacionMS.client.UsuarioClient;
import cl.duoc.notificacionMS.dto.NotificacionDTO;
import cl.duoc.notificacionMS.dto.UsuarioDTO;
import cl.duoc.notificacionMS.model.Notificacion;
import cl.duoc.notificacionMS.repository.NotificacionRepository;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repo;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacionEjemplo;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        notificacionEjemplo = new Notificacion();
        notificacionEjemplo.setId(1);
        notificacionEjemplo.setMensaje("Tu solicitud fue aprobada");
        notificacionEjemplo.setFechaEmision(new Date());
        notificacionEjemplo.setUsuarioId(1);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1);
        usuarioDTO.setNombre("Carlos");
        usuarioDTO.setApellido("González");
    }

    @Test
    void listar_retornaListaConNotificaciones() {
        // ARRANGE
        List<Notificacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(notificacionEjemplo);
        when(repo.findAll()).thenReturn(listaFalsa);

        // ACT
        List<Notificacion> resultado = notificacionService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Tu solicitud fue aprobada", resultado.get(0).getMensaje());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(repo.findById(1)).thenReturn(Optional.of(notificacionEjemplo));

        // ACT
        Notificacion resultado = notificacionService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Tu solicitud fue aprobada", resultado.getMensaje());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(repo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.buscarPorId(99);
        });

        assertEquals("Notificacion no encontrada", ex.getMessage());
    }

    @Test
    void guardar() {
        // ARRANGE: usuario existe
        when(usuarioClient.obtenUsuario(1)).thenReturn(usuarioDTO);
        when(repo.save(notificacionEjemplo)).thenReturn(notificacionEjemplo);

        // ACT
        Notificacion resultado = notificacionService.guardar(notificacionEjemplo);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Tu solicitud fue aprobada", resultado.getMensaje());
        verify(repo, times(1)).save(notificacionEjemplo);
    }

    @Test
    void guardar_usuarioNoExiste() {
        // ARRANGE: el cliente retorna null
        when(usuarioClient.obtenUsuario(1)).thenReturn(null);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.guardar(notificacionEjemplo);
        });

        assertEquals("Usuario no existe", ex.getMessage());
        verify(repo, never()).save(any(Notificacion.class));
    }

    @Test
    void obtenerNotificacion() {
        // ARRANGE
        when(repo.findById(1)).thenReturn(Optional.of(notificacionEjemplo));
        when(usuarioClient.obtenUsuario(1)).thenReturn(usuarioDTO);

        // ACT
        NotificacionDTO resultado = notificacionService.obtenerNotificacion(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Tu solicitud fue aprobada", resultado.getMensaje());
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    void obtenerNotificacion_noEncontrada() {
        // ARRANGE
        when(repo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.obtenerNotificacion(99);
        });

        assertEquals("Notificacion no encontrada", ex.getMessage());
    }

    @Test
    void obtenerNotificacion_usuarioNoEncontrado() {
        // ARRANGE
        when(repo.findById(1)).thenReturn(Optional.of(notificacionEjemplo));
        when(usuarioClient.obtenUsuario(1)).thenReturn(null);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            notificacionService.obtenerNotificacion(1);
        });

        assertEquals("usuario no encontrado", ex.getMessage());
    }
}