package cl.duoc.Autenticacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.Autenticacion.Client.UsuarioClient;
import cl.duoc.Autenticacion.dto.AutenticacionDTO;
import cl.duoc.Autenticacion.dto.UsuarioDTO;
import cl.duoc.Autenticacion.model.Autenticacion;
import cl.duoc.Autenticacion.repository.AutenticacionRepository;

@ExtendWith(MockitoExtension.class)
public class AutenticacionServiceTest {

    @Mock
    private AutenticacionRepository authRepo;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private AutenticacionService autenticacionService;

    private Autenticacion authEjemplo;
    private AutenticacionDTO authDTO;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        authEjemplo = new Autenticacion();
        authEjemplo.setIdAuth(1);
        authEjemplo.setIdUsuario(1);
        authEjemplo.setEstado("activo");

        authDTO = new AutenticacionDTO();
        authDTO.setIdUsuario(1);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(1);
        usuarioDTO.setNombre("Carlos");
        usuarioDTO.setApellido("González");
    }

    @Test
    void registrar_exitoso_nuevoUsuario() {
        // ARRANGE: usuario existe y no tiene auth registrada aún
        when(usuarioClient.buscarUsuario(1)).thenReturn(usuarioDTO);
        when(authRepo.findByIdUsuario(1)).thenReturn(null);
        when(authRepo.save(any(Autenticacion.class))).thenReturn(authEjemplo);

        // ACT
        Autenticacion resultado = autenticacionService.registrar(authDTO);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("activo", resultado.getEstado());
        verify(authRepo, times(1)).save(any(Autenticacion.class));
    }

    @Test
    void registrar_retornaExistentesSiYaRegistrado() {
        // ARRANGE: el usuario ya tiene auth registrada
        when(usuarioClient.buscarUsuario(1)).thenReturn(usuarioDTO);
        when(authRepo.findByIdUsuario(1)).thenReturn(authEjemplo);

        // ACT
        Autenticacion resultado = autenticacionService.registrar(authDTO);

        // ASSERT: se retorna la existente y no se vuelve a guardar
        assertEquals(1, resultado.getIdAuth());
        verify(authRepo, never()).save(any(Autenticacion.class));
    }

    @Test
    void registrar_usuarioNoExiste() {
        // ARRANGE: el cliente feign retorna null
        when(usuarioClient.buscarUsuario(1)).thenReturn(null);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            autenticacionService.registrar(authDTO);
        });

        assertEquals("El usuario no existe en UsuarioMS", ex.getMessage());
    }

    @Test
    void login_exitoso() {
        // ARRANGE
        when(usuarioClient.buscarUsuario(1)).thenReturn(usuarioDTO);
        when(authRepo.findByIdUsuario(1)).thenReturn(authEjemplo);
        when(authRepo.save(any(Autenticacion.class))).thenReturn(authEjemplo);

        // ACT
        String resultado = autenticacionService.login(authDTO);

        // ASSERT
        assertEquals("Login exitoso", resultado);
        verify(authRepo, times(1)).save(authEjemplo);
    }

    @Test
    void login_usuarioNoEncontradoEnUsuarioMS() {
        // ARRANGE: Feign retorna null
        when(usuarioClient.buscarUsuario(1)).thenReturn(null);

        // ACT
        String resultado = autenticacionService.login(authDTO);

        // ASSERT
        assertEquals("Usuario no encontrado en UsuarioMS", resultado);
    }

    @Test
    void login_usuarioNoTieneAuth() {
        // ARRANGE: existe en UsuarioMS pero no tiene registro de auth
        when(usuarioClient.buscarUsuario(1)).thenReturn(usuarioDTO);
        when(authRepo.findByIdUsuario(1)).thenReturn(null);

        // ACT
        String resultado = autenticacionService.login(authDTO);

        // ASSERT
        assertEquals("Usuario no encontrado", resultado);
    }

    @Test
    void login_usuarioBloqueado() {
        // ARRANGE: auth existe pero estado es inactivo
        authEjemplo.setEstado("inactivo");
        when(usuarioClient.buscarUsuario(1)).thenReturn(usuarioDTO);
        when(authRepo.findByIdUsuario(1)).thenReturn(authEjemplo);

        // ACT
        String resultado = autenticacionService.login(authDTO);

        // ASSERT
        assertEquals("Usuario bloqueado", resultado);
    }
}
