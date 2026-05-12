package cl.duoc.donacionMS.config;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.donacionMS.model.Donacion;
import cl.duoc.donacionMS.repository.DonacionRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(DonacionRepository repo){
        return args -> {
            if (repo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos");
            } else {
                Donacion don1 = new Donacion(null, 10000, new Date(), "Transferencia", 1, 2);
                
                Donacion don2 = new Donacion(null, 25000, new Date(), "Tarjeta", 1,3);

                Donacion don3 = new Donacion(null, 5000, new Date(), "Efectivo", 1, 1);

                repo.save(don1);
                repo.save(don2);
                repo.save(don3);

                System.out.println("Base de datos cargada");
            }
            
        };
    }

}
