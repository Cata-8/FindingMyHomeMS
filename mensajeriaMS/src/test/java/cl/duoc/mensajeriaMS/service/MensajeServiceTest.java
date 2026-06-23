package cl.duoc.mensajeriaMS.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.mensajeriaMS.client.UsuarioClient;
import cl.duoc.mensajeriaMS.dto.MensajeDTO;
import cl.duoc.mensajeriaMS.dto.UsuarioDTO;
import cl.duoc.mensajeriaMS.model.Mensaje;
import cl.duoc.mensajeriaMS.repository.MensajeRepository;

@ExtendWith(MockitoExtension.class)
public class MensajeServiceTest {
    @Mock
    private MensajeRepository mensajeRepo;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private MensajeService mensajeService;

    private Mensaje mensajeEjemplo;
    private MensajeDTO mensajeDTO;
    private UsuarioDTO remitenteDTO;
    private UsuarioDTO destinatarioDTO;

    @BeforeEach
    void setUp() {
        mensajeEjemplo = new Mensaje();
        mensajeEjemplo.setId(1);
        mensajeEjemplo.setIdRemitente(1);
        mensajeEjemplo.setIdDestinatario(2);
        mensajeEjemplo.setContenido("Hola, quiero adoptar la mascota");
        mensajeEjemplo.setFechaEnvio(LocalDate.now());
        mensajeEjemplo.setLeido(false);

        mensajeDTO = new MensajeDTO();
        mensajeDTO.setIdRemitente(1);
        mensajeDTO.setIdDestinatario(2);
        mensajeDTO.setContenido("Hola, quiero adoptar la mascota");

        remitenteDTO = new UsuarioDTO();
        remitenteDTO.setIdUsuario(1);
        remitenteDTO.setNombre("Carlos");
        remitenteDTO.setApellido("González");

        destinatarioDTO = new UsuarioDTO();
        destinatarioDTO.setIdUsuario(2);
        destinatarioDTO.setNombre("María");
        destinatarioDTO.setApellido("López");
    }

    @Test
    void listar_retornaListaConMensajes() {
        // ARRANGE
        List<Mensaje> listaFalsa = new ArrayList<>();
        listaFalsa.add(mensajeEjemplo);
        when(mensajeRepo.findAll()).thenReturn(listaFalsa);

        // ACT
        List<Mensaje> resultado = mensajeService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Hola, quiero adoptar la mascota", resultado.get(0).getContenido());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(mensajeRepo.findById(1)).thenReturn(Optional.of(mensajeEjemplo));

        // ACT
        Mensaje resultado = mensajeService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertFalse(resultado.isLeido());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(mensajeRepo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            mensajeService.buscarPorId(99);
        });

        assertEquals("Mensaje no encontrado", ex.getMessage());
    }

    @Test
    void crearMensaje_exitoso() {
        // ARRANGE
        when(usuarioClient.buscarUsuario(1)).thenReturn(remitenteDTO);
        when(usuarioClient.buscarUsuario(2)).thenReturn(destinatarioDTO);
        when(mensajeRepo.save(any(Mensaje.class))).thenReturn(mensajeEjemplo);

        // ACT
        Mensaje resultado = mensajeService.crearMensaje(mensajeDTO);

        // ASSERT
        assertNotNull(resultado);
        assertFalse(resultado.isLeido());
        assertEquals("Hola, quiero adoptar la mascota", resultado.getContenido());
        verify(mensajeRepo, times(1)).save(any(Mensaje.class));
    }

    @Test
    void crearMensaje_usuarioNoExiste() {
        // ARRANGE: falla al buscar el remitente
        doThrow(new RuntimeException()).when(usuarioClient).buscarUsuario(1);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            mensajeService.crearMensaje(mensajeDTO);
        });

        assertEquals("Usuario no existe", ex.getMessage());
        verify(mensajeRepo, never()).save(any(Mensaje.class));
    }

    @Test
    void marcarComoLeido_exitoso() {
        // ARRANGE
        when(mensajeRepo.findById(1)).thenReturn(Optional.of(mensajeEjemplo));
        mensajeEjemplo.setLeido(true);
        when(mensajeRepo.save(mensajeEjemplo)).thenReturn(mensajeEjemplo);

        // ACT
        Mensaje resultado = mensajeService.marcarComoLeido(1);

        // ASSERT
        assertTrue(resultado.isLeido());
        verify(mensajeRepo, times(1)).save(mensajeEjemplo);
    }

    @Test
    void marcarComoLeido_noEncontrado() {
        // ARRANGE
        when(mensajeRepo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            mensajeService.marcarComoLeido(99);
        });

        assertEquals("Mensaje no encontrado", ex.getMessage());
    }

    @Test
    void eliminar_exitoso() {
        // ARRANGE
        when(mensajeRepo.findById(1)).thenReturn(Optional.of(mensajeEjemplo));

        // ACT + ASSERT
        assertDoesNotThrow(() -> mensajeService.eliminar(1));
        verify(mensajeRepo, times(1)).delete(mensajeEjemplo);
    }

    @Test
    void eliminar_noEncontrado() {
        // ARRANGE
        when(mensajeRepo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            mensajeService.eliminar(99);
        });

        assertEquals("Mensaje no encontrado", ex.getMessage());
        verify(mensajeRepo, never()).delete(any(Mensaje.class));
    }
}
