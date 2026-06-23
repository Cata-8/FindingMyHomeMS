package cl.duoc.fichaSaludMS.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import cl.duoc.fichaSaludMS.client.MascotaClient;
import cl.duoc.fichaSaludMS.dto.MascotaDTO;
import cl.duoc.fichaSaludMS.model.FichaSalud;
import cl.duoc.fichaSaludMS.repository.FichaSaludRepository;

@ExtendWith(MockitoExtension.class)
public class FichaSaludServiceTest {

    @Mock
    private FichaSaludRepository repo;

    @Mock
    private MascotaClient mascotaClient;

    @InjectMocks
    private FichaSaludService fichaSaludService;

    private FichaSalud fichaEjemplo;
    private MascotaDTO mascotaDTO;

    @BeforeEach
    void setUp() {
        fichaEjemplo = new FichaSalud();
        fichaEjemplo.setId(1);
        fichaEjemplo.setEnfermedades("ninguna");
        fichaEjemplo.setEstadoReproductivo("esterilizado");
        fichaEjemplo.setMicroship(false);
        fichaEjemplo.setDesparasitado(true);
        fichaEjemplo.setIdMascota(1);
        fichaEjemplo.setRegistroVacunas(new ArrayList<>());

        mascotaDTO = new MascotaDTO();
        mascotaDTO.setId(1);
        mascotaDTO.setNombre("Luna");
        mascotaDTO.setTipo("Perro");
    }

    @Test
    void listar_retornaListaConFichas() {
        // ARRANGE
        List<FichaSalud> listaFalsa = new ArrayList<>();
        listaFalsa.add(fichaEjemplo);
        when(repo.findAll()).thenReturn(listaFalsa);

        // ACT
        List<FichaSalud> resultado = fichaSaludService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("ninguna", resultado.get(0).getEnfermedades());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(repo.findById(1)).thenReturn(Optional.of(fichaEjemplo));

        // ACT
        FichaSalud resultado = fichaSaludService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("esterilizado", resultado.getEstadoReproductivo());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(repo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            fichaSaludService.buscarPorId(99);
        });

        assertEquals("Ficha no encontrada", ex.getMessage());
    }

    @Test
    void guardar_exitoso() {
        // ARRANGE
        when(mascotaClient.obtenerMascotaDTO(1)).thenReturn(mascotaDTO);
        when(repo.save(fichaEjemplo)).thenReturn(fichaEjemplo);

        // ACT
        FichaSalud resultado = fichaSaludService.guardar(fichaEjemplo);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(repo, times(1)).save(fichaEjemplo);
    }

    @Test
    void guardar_mascotaNoExiste() {
        // ARRANGE
        when(mascotaClient.obtenerMascotaDTO(1)).thenReturn(null);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            fichaSaludService.guardar(fichaEjemplo);
        });

        assertEquals("Mascota no existe", ex.getMessage());
        verify(repo, never()).save(any(FichaSalud.class));
    }

    @Test
    void eliminar_exitoso() {
        // ARRANGE
        when(repo.existsById(1)).thenReturn(true);

        // ACT + ASSERT
        assertDoesNotThrow(() -> fichaSaludService.eliminar(1));
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        // ARRANGE
        when(repo.existsById(99)).thenReturn(false);

        // ACT + ASSERT
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            fichaSaludService.eliminar(99);
        });

        assertEquals("Ficha no existe", ex.getMessage());
        verify(repo, never()).deleteById(anyInt());
    }
}
