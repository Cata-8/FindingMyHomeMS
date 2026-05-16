package cl.duoc.UsuarioMS.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.UsuarioMS.model.Adoptante;
import cl.duoc.UsuarioMS.model.Refugio;
import cl.duoc.UsuarioMS.model.Usuario;
import cl.duoc.UsuarioMS.repository.AdoptanteRepository;
import cl.duoc.UsuarioMS.repository.RefugioRepository;
import cl.duoc.UsuarioMS.repository.UsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository, AdoptanteRepository adoptanteRepository, RefugioRepository refugioRepository) {

        return args -> {

            Usuario usuario1 = new Usuario(null,"Carlos","Pino","correo@correo.cl","12345A","+56955555555",LocalDateTime.now(),null,null);
            Usuario usuario2 = new Usuario(null, "Josefa", "Quiroz", "correo2@correo.cl", "12345B", "+56966666666", LocalDateTime.now(), null, null);
            Usuario usuario3 = new Usuario(null, "Francisca", "Silva", "correo3@correo.cl", "12345C", "+56977777777", LocalDateTime.now(), null, null);
            Usuario usuario4 = new Usuario(null,"Kevin","Santander","correo4@correo.cl","12345D","+56988888888",LocalDateTime.now(),null,null);

            
            Adoptante adoptante1 = new Adoptante(null,usuario1);
            Adoptante adoptante2 = new Adoptante(null,usuario2);

            Refugio refugio1 = new Refugio(null, "Patitas Felices", "Av. Independencia 123", "Refugio centrado en el rescate de Perros y Gatos y en la busqueda de familias dispuestas a integrarlos con amor", "+56911111111", usuario3);
            Refugio refugio2 = new Refugio(null, "Marca Animal", "Av. Recoleta 123", "Refugio centrado en el rescate de Perros y Gatos y en la busqueda de familias dispuestas a integrarlos con amor", "+56922222222", usuario4);

            usuario1.setAdoptante(adoptante1);
            usuario2.setAdoptante(adoptante2);

            usuario3.setRefugio(refugio1);
            usuario4.setRefugio(refugio2);

            usuarioRepository.save(usuario1);
            usuarioRepository.save(usuario2);
            usuarioRepository.save(usuario3);
            usuarioRepository.save(usuario4);

            System.out.println("Datos cargados correctamente");
        };
    }

}
