package cl.Duoc.HistorialAdopcionMS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.Duoc.HistorialAdopcionMS.model.HistorialAdopcion;
import cl.Duoc.HistorialAdopcionMS.repository.HistorialRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(HistorialRepository repo){

         return args -> {
            if (repo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos");
            } else {
                HistorialAdopcion h1 = new HistorialAdopcion();
                h1.setIdUsuario(1);
                h1.setIdMascota(2);

                HistorialAdopcion h2 = new HistorialAdopcion();
                h2.setIdUsuario(2);
                h2.setIdMascota(3);

                repo.save(h1);
                repo.save(h2);

                System.out.println("Datos cargados");
            }
         };
    }
}
