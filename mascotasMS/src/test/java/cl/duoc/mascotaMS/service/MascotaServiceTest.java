package cl.duoc.mascotaMS.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import cl.duoc.mascotaMS.model.Mascota;
import cl.duoc.mascotaMS.repository.MascotaRepository;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceTest {

    @Mock //no es el repository real, solo va a ser una simulacion de repo
    private MascotaRepository mascotaRepo;

    //el service real con el repo simulado inyectado
    @InjectMocks
    private MascotaService mascotaService;

    private Mascota mascotaEjemplo;

    @BeforeEach
    void setUp(){

        mascotaEjemplo = new Mascota();
        mascotaEjemplo.setId(1);
        mascotaEjemplo.setNombre("Leo");
        mascotaEjemplo.setTipo("Perro");
        mascotaEjemplo.setEdad(3);
        mascotaEjemplo.setDescripcion("Tranquilo y cariñoso");
        mascotaEjemplo.setEstado("Disponible");
        mascotaEjemplo.setUbicacion("Talca");
        mascotaEjemplo.setFecha_publicacion(new Date());
    }

    @Test
    void listar_retornaListaMacotas(){
        // ARRANGE: creamos la lista manualmente
        List<Mascota> listaFalsa = new ArrayList<>();
        listaFalsa.add(mascotaEjemplo);

        // le decimos al repositorio falso que devuelva esa lista cuando se llame findAll()
        when(mascotaRepo.findAll()).thenReturn(listaFalsa);

        // ACT: llamamos al método real del servicio
        List<Mascota> resultado = mascotaService.listar();

        // ASSERT: verificamos el resultado
        assertEquals(1, resultado.size());
        assertEquals("Leo", resultado.get(0).getNombre());
    }

    @Test
    void buscarPorId_encontrado(){
        // ARRANGE: preparamos la prueba, le decimos que hacer
        Optional<Mascota> optionalMascota = Optional.of(mascotaEjemplo);
        when(mascotaRepo.findById(1)).thenReturn(optionalMascota);

        //ACT: llamamos al metodo real
        Mascota resultado = mascotaService.buscarPorId(1);

        //ASSERT: verificamos si el doctor que retornó es el correcto
        //         (valor que deberia tener, origen)
        assertEquals(1, resultado.getId());
        assertEquals("Leo", resultado.getNombre());

    }

    @Test
    void buscarPorId_noEncontrado(){
        // ARRANGE: preparamos la prueba pero para que retorne una mascota vacia
        Optional<Mascota> mascotaVacia = Optional.empty();
        when(mascotaRepo.findById(99)).thenReturn(mascotaVacia);

        //ACT + ASSERT: verificamos si lanza la excepcion correcta
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mascotaService.buscarPorId(99);
        });

        assertEquals("Mascota no encontrada", exception.getMessage());

    }

    @Test
    void guardar(){
        // ARRANGE: configuramos que el repository retorne la mascota guardada
        when(mascotaRepo.save(mascotaEjemplo)).thenReturn(mascotaEjemplo); 

        //ACT
        Mascota resultado = mascotaService.guardar(mascotaEjemplo);

        //ASSERT
        assertEquals("Leo", resultado.getNombre());
        verify(mascotaRepo, times(1)).save(mascotaEjemplo); // verificamos que save fue llamado
    }

    @Test
    void eliminar(){
        //ARRANGE: la mascota existe
        when(mascotaRepo.existsById(1)).thenReturn(true);

        //ACT + ASSERT: no debe lanzar un error/exception
        assertDoesNotThrow(() -> mascotaService.eliminar(1));

        //verificamos que el deleteById fue exitoso solo una vez
        verify(mascotaRepo, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste(){
        //ARRANGE: la mascota existe
        when(mascotaRepo.existsById(99)).thenReturn(false);

        //ACT + ASSERT: no debe lanzar un error/exception, debe lanzar excepción
        RuntimeException ex = assertThrows(RuntimeException.class, () ->{
            mascotaService.eliminar(99);
        });

        assertDoesNotThrow(() -> mascotaService.eliminar(1));

        // verificamos que deleteById NUNCA fue llamado
        verify(mascotaRepo, never()).deleteById(99);

    }
    
}
