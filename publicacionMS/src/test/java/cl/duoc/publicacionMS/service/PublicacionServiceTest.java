package cl.duoc.publicacionMS.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import cl.duoc.publicacionMS.client.MascotaClient;
import cl.duoc.publicacionMS.dto.MascotaDTO;
import cl.duoc.publicacionMS.dto.PublicacionDTO;
import cl.duoc.publicacionMS.model.Publicacion;
import cl.duoc.publicacionMS.repository.PublicacionRepository;

@ExtendWith(MockitoExtension.class)
public class PublicacionServiceTest {
    @Mock
    private PublicacionRepository repo;
 
    @Mock
    private MascotaClient mascotaClient;
 
    @InjectMocks
    private PublicacionService publicacionService;
 
    private Publicacion publicacionEjemplo;
    private MascotaDTO mascotaDTO;
 
    @BeforeEach
    void setUp() {
        publicacionEjemplo = new Publicacion();
        publicacionEjemplo.setId(1);
        publicacionEjemplo.setTitulo("Luna busca hogar");
        publicacionEjemplo.setDescripcion("Perrita juguetona busca familia amorosa");
        publicacionEjemplo.setFechaPublicacion(new Date());
        publicacionEjemplo.setEstado("Activa");
        publicacionEjemplo.setIdMascota(1);
 
        mascotaDTO = new MascotaDTO();
        mascotaDTO.setId(1);
        mascotaDTO.setNombre("Luna");
        mascotaDTO.setTipo("Perro");
    }
 
    @Test
    void listar_retornaListaConPublicaciones() {
        // ARRANGE
        List<Publicacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(publicacionEjemplo);
        when(repo.findAll()).thenReturn(listaFalsa);
 
        // ACT
        List<Publicacion> resultado = publicacionService.listar();
 
        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Luna busca hogar", resultado.get(0).getTitulo());
    }
 
    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(repo.findById(1)).thenReturn(Optional.of(publicacionEjemplo));
 
        // ACT
        Publicacion resultado = publicacionService.buscarPorId(1);
 
        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Activa", resultado.getEstado());
    }
 
    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(repo.findById(99)).thenReturn(Optional.empty());
 
        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            publicacionService.buscarPorId(99);
        });
 
        assertEquals("Publicación no encontrada", ex.getMessage());
    }
 
    @Test
    void guardar_exitoso() {
        // ARRANGE: mascota existe
        when(mascotaClient.obtenerMascotaDTO(1)).thenReturn(mascotaDTO);
        when(repo.save(publicacionEjemplo)).thenReturn(publicacionEjemplo);
 
        // ACT
        Publicacion resultado = publicacionService.guardar(publicacionEjemplo);
 
        // ASSERT
        assertNotNull(resultado);
        assertEquals("Luna busca hogar", resultado.getTitulo());
        verify(repo, times(1)).save(publicacionEjemplo);
    }
 
    @Test
    void guardar_mascotaNoExiste() {
        // ARRANGE: el cliente retorna null
        when(mascotaClient.obtenerMascotaDTO(1)).thenReturn(null);
 
        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            publicacionService.guardar(publicacionEjemplo);
        });
 
        assertEquals("mascota no existe", ex.getMessage());
        verify(repo, never()).save(any(Publicacion.class));
    }
 
    @Test
    void obtenerDetalle_exitoso() {
        // ARRANGE
        when(repo.findById(1)).thenReturn(Optional.of(publicacionEjemplo));
        when(mascotaClient.obtenerMascotaDTO(1)).thenReturn(mascotaDTO);
 
        // ACT
        PublicacionDTO resultado = publicacionService.obtenerDetalle(1);
 
        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Luna busca hogar", resultado.getTitulo());
        assertEquals(1, resultado.getIdMascota());
    }
 
    @Test
    void obtenerDetalle_publicacionNoEncontrada() {
        // ARRANGE
        when(repo.findById(99)).thenReturn(Optional.empty());
 
        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            publicacionService.obtenerDetalle(99);
        });
 
        assertEquals("Publicación no encontrada", ex.getMessage());
    }
 
    @Test
    void eliminar_exitoso() {
        // ARRANGE
        when(repo.existsById(1)).thenReturn(true);
 
        // ACT + ASSERT
        assertDoesNotThrow(() -> publicacionService.eliminar(1));
        verify(repo, times(1)).deleteById(1);
    }
 
    @Test
    void eliminar_noExiste() {
        // ARRANGE
        when(repo.existsById(99)).thenReturn(false);
 
        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            publicacionService.eliminar(99);
        });
 
        assertEquals("Publicacion no existe", ex.getMessage());
        verify(repo, never()).deleteById(99);
    }
}
