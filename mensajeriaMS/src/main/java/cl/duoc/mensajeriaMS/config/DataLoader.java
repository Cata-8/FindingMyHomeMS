package cl.duoc.mensajeriaMS.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.mensajeriaMS.model.Mensaje;
import cl.duoc.mensajeriaMS.repository.MensajeRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(MensajeRepository mensajeRepository){
        return args -> {
            if (mensajeRepository.count() > 0) {
                System.out.println("No se cargó nada poruqe ya hay datos");
            } else {
                
            Mensaje m1 = new Mensaje();
            m1.setIdRemitente(1);
            m1.setIdDestinatario(2);
            m1.setContenido("Hola, quiero adoptar la mascota");
            m1.setFechaEnvio(LocalDate.now());
            m1.setLeido(false);

            Mensaje m2 = new Mensaje();
            m2.setIdRemitente(2);
            m2.setIdDestinatario(1);
            m2.setContenido("Perfecto, coordinemos");
            m2.setFechaEnvio(LocalDate.now());
            m2.setLeido(true);

            mensajeRepository.save(m1);
            mensajeRepository.save(m2);
                




                System.out.println("Base de datos cargada");
            }
        };
    }

}
