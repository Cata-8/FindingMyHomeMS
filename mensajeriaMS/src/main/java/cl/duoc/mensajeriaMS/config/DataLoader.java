package cl.duoc.mensajeriaMS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.mensajeriaMS.repository.MensajeRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(MensajeRepository mensajeRepository){
        return args -> {
            if (mensajeRepository.count() > 0) {
                System.out.println("No se cargó nada poruqe ya hay datos");
            } else {
                




                System.out.println("Base de datos cargada");
            }
        };
    }

}
