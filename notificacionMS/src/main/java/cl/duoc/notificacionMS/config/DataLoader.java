package cl.duoc.notificacionMS.config;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.notificacionMS.model.Notificacion;
import cl.duoc.notificacionMS.repository.NotificacionRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(NotificacionRepository notificacionRepo){
        return args -> {
            if (notificacionRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos");
            } else {
                Notificacion not1 = new Notificacion(null, "Tu solicitud de adopción fue aprobada", new Date(), 1);
                Notificacion not2 = new Notificacion(null, "Tienes un nuevo mensaje", new Date(), 2);
                Notificacion not3 = new Notificacion(null, "Hay una nueva solicitud de adopción", new Date(), 3);

                notificacionRepo.save(not1);
                notificacionRepo.save(not2);
                notificacionRepo.save(not3);

                System.out.println("Base de datos cargada");
            }
        };
    }

}
