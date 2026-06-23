package cl.duoc.UsuarioMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.UsuarioMS.client.AutenticacionClient;
import cl.duoc.UsuarioMS.dto.AdoptanteDTO;
import cl.duoc.UsuarioMS.dto.AutenticacionDTO;
import cl.duoc.UsuarioMS.dto.RefugioDTO;
import cl.duoc.UsuarioMS.model.Adoptante;
import cl.duoc.UsuarioMS.model.Refugio;
import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.repository.AdoptanteRepository;
import cl.duoc.UsuarioMS.repository.RefugioRepository;
import cl.duoc.UsuarioMS.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock //simulación
    private UsuarioRepository repoUsuario;

    @Mock
    private AdoptanteRepository adoptanteRepo;
 
    @Mock
    private RefugioRepository refugioRepo;
 
    @Mock
    private AutenticacionClient authClient;

    @InjectMocks //servicio real con el repo
    private UsuarioService servUsuario;

    private Usuario usuarioEjemplo;
    private AdoptanteDTO adoptanteDTOEjemplo;
    private RefugioDTO refugioDTOEjemplo;

    @BeforeEach
    void setUp(){
        
        usuarioEjemplo = new Usuario(); 
        usuarioEjemplo.setIdUsuario(1);
        usuarioEjemplo.setNombre("Juan");
        usuarioEjemplo.setApellido("Pérez");
        usuarioEjemplo.setTelefono("2353636322");
        usuarioEjemplo.setEmail("juanperez@gmail.com");

        adoptanteDTOEjemplo = new AdoptanteDTO();
        adoptanteDTOEjemplo.setNombre("Juan");
        adoptanteDTOEjemplo.setApellido("Pérez");
        adoptanteDTOEjemplo.setEmail("juanperez@gmail.com");
        adoptanteDTOEjemplo.setTelefono("912345678");
        adoptanteDTOEjemplo.setPassword("1234");
 
        refugioDTOEjemplo = new RefugioDTO();
        refugioDTOEjemplo.setNombre("Ana");
        refugioDTOEjemplo.setApellido("López");
        refugioDTOEjemplo.setEmail("refugio@gmail.com");
        refugioDTOEjemplo.setTelefono("987654321");
        refugioDTOEjemplo.setPassword("abcd");
        refugioDTOEjemplo.setNombreRefugio("Patitas Felices");
        refugioDTOEjemplo.setDireccion("Av. Siempre Viva 123");
        refugioDTOEjemplo.setDescripcion("Refugio de animales abandonados");
        refugioDTOEjemplo.setTelefonoContacto("222333444");
    }

    @Test
    void listarUsuarios_retornaListaConUsuarios() {
        // ARRANGE
        List<Usuario> listaFalsa = new ArrayList<>();
        listaFalsa.add(usuarioEjemplo);
        when(repoUsuario.findAll()).thenReturn(listaFalsa);
 
        // ACT
        List<Usuario> resultado = servUsuario.listarUsuarios();
 
        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("juanperez@gmail.com", resultado.get(0).getEmail());
    }
 
    @Test
    void listarUsuarios_retornaListaVacia() {
        // ARRANGE
        when(repoUsuario.findAll()).thenReturn(new ArrayList<>());
 
        // ACT
        List<Usuario> resultado = servUsuario.listarUsuarios();
 
        // ASSERT
        assertTrue(resultado.isEmpty());
    }

    @Test
    void buscarUsuario_encontrado(){

        //ARRANGE = preparamos la prueba
        Optional<Usuario> optionalUsuario = Optional.of(usuarioEjemplo);
        when(repoUsuario.findById(1)).thenReturn(optionalUsuario);

        //ACT = llamamos al metodo real
        Usuario resultado = servUsuario.buscarUsuario(1);

        //ASSERT = verificación
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
        assertEquals("2353636322", resultado.getTelefono());
        assertEquals("juanperez@gmail.com", resultado.getEmail());

    }

    @Test
    void buscarUsuario_noEncontrado(){
        // ARRANGE: el repo retorna vacío, el servicio usa orElse(null)
        when(repoUsuario.findById(99)).thenReturn(Optional.empty());
 
        // ACT
        Usuario resultado = servUsuario.buscarUsuario(99);
 
        // ASSERT: el controller se encarga del 404, el service retorna null
        assertNull(resultado);
    }

    @Test
    void eliminarUsuario_exitoso() {
        // ARRANGE
        doNothing().when(repoUsuario).deleteById(1);
 
        // ACT + ASSERT
        assertDoesNotThrow(() -> servUsuario.eliminarUsuario(1));
        verify(repoUsuario, times(1)).deleteById(1);
    }
 
    @Test
    void eliminarUsuario_siIdEsNull() {
        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            servUsuario.eliminarUsuario(null);
        });
 
        assertEquals("Usuario no encontrado", ex.getMessage());
        verify(repoUsuario, never()).deleteById(any());
    }

    @Test
    void guardarAdoptante_exitoso() {
        // ARRANGE
        when(repoUsuario.save(any(Usuario.class))).thenReturn(usuarioEjemplo);
        doNothing().when(authClient).registrar(any(AutenticacionDTO.class));
 
        Adoptante adoptanteGuardado = new Adoptante();
        adoptanteGuardado.setUsuario(usuarioEjemplo);
        when(adoptanteRepo.save(any(Adoptante.class))).thenReturn(adoptanteGuardado);
 
        // ACT
        Adoptante resultado = servUsuario.guardarAdoptante(adoptanteDTOEjemplo);
 
        // ASSERT
        assertNotNull(resultado);
        assertEquals(usuarioEjemplo, resultado.getUsuario());
        verify(repoUsuario, times(1)).save(any(Usuario.class));
        verify(authClient, times(1)).registrar(any(AutenticacionDTO.class));
        verify(adoptanteRepo, times(1)).save(any(Adoptante.class));
    }

    @Test
    void guardarAdoptante_siAuthFalla() {
        // ARRANGE
        when(repoUsuario.save(any(Usuario.class))).thenReturn(usuarioEjemplo);
        doThrow(new RuntimeException("Error al registrar en auth")).when(authClient).registrar(any(AutenticacionDTO.class));
 
        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            servUsuario.guardarAdoptante(adoptanteDTOEjemplo);
        });
 
        assertEquals("Error al registrar en auth", ex.getMessage());
        verify(adoptanteRepo, never()).save(any(Adoptante.class));
    }

    @Test
    void guardarRefugio_exitoso() {
        // ARRANGE
        Usuario usuarioRefugio = new Usuario();
        usuarioRefugio.setIdUsuario(2);
        usuarioRefugio.setNombre("Ana");
        usuarioRefugio.setApellido("López");
        usuarioRefugio.setEmail("refugio@gmail.com");
 
        when(repoUsuario.save(any(Usuario.class))).thenReturn(usuarioRefugio);
        doNothing().when(authClient).registrar(any(AutenticacionDTO.class));
 
        Refugio refugioGuardado = new Refugio();
        refugioGuardado.setUsuario(usuarioRefugio);
        refugioGuardado.setNombreRefugio("Patitas Felices");
        refugioGuardado.setDireccion("Av. Siempre Viva 123");
        refugioGuardado.setDescripcion("Refugio de animales abandonados");
        refugioGuardado.setTelefonoContacto("222333444");
        when(refugioRepo.save(any(Refugio.class))).thenReturn(refugioGuardado);
 
        // ACT
        Refugio resultado = servUsuario.guardarRefugio(refugioDTOEjemplo);
 
        // ASSERT
        assertNotNull(resultado);
        assertEquals("Patitas Felices", resultado.getNombreRefugio());
        assertEquals("Av. Siempre Viva 123", resultado.getDireccion());
        verify(repoUsuario, times(1)).save(any(Usuario.class));
        verify(authClient, times(1)).registrar(any(AutenticacionDTO.class));
        verify(refugioRepo, times(1)).save(any(Refugio.class));
    }
 
    @Test
    void guardarRefugio_siAuthFalla() {
        // ARRANGE
        when(repoUsuario.save(any(Usuario.class))).thenReturn(usuarioEjemplo);
        doThrow(new RuntimeException("Error al registrar en auth")).when(authClient).registrar(any(AutenticacionDTO.class));
 
        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            servUsuario.guardarRefugio(refugioDTOEjemplo);
        });
 
        assertEquals("Error al registrar en auth", ex.getMessage());
        verify(refugioRepo, never()).save(any(Refugio.class));
    }

}
