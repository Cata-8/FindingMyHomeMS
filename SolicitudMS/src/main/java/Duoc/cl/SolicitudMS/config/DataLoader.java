package Duoc.cl.SolicitudMS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Duoc.cl.SolicitudMS.model.Solicitud;
import Duoc.cl.SolicitudMS.repository.SolicitudRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initData(SolicitudRepository soliRepo){

        return args -> {
            if (soliRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos");
            } else {
                Solicitud s1 = new Solicitud();
                s1.setMensaje("Quiero adoptarlo");
                s1.setEstado("pendiente");
                s1.setIdMascota(1);
                s1.setIdUsuarioAdoptante(1);

                Solicitud s2 = new Solicitud();
                s2.setMensaje("Quiero adoptarlo, tengo espacio y tiempo para cuidarlo");
                s2.setEstado("aprobada");
                s2.setIdMascota(3);
                s2.setIdUsuarioAdoptante(2);

                soliRepo.save(s1);
                soliRepo.save(s2);

                System.out.println("Datos cargados correctamente");
            }

        };

    }
}
