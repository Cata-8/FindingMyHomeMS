package cl.duoc.mascotaMS.config;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.mascotaMS.model.Mascota;
import cl.duoc.mascotaMS.repository.MascotaRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(MascotaRepository mascotaRepo){
        return args -> {
            if (mascotaRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos");
            } else {
                Mascota masc1 = new Mascota(null,"Luna","Perro",3,"Perrita juguetona y amigable","Disponible","Santiago",new Date());

                Mascota masc2 = new Mascota(null,"Milo","Gato",2,"Gato tranquilo y cariñoso","Adoptado","Valparaíso",new Date());

                Mascota masc3 = new Mascota(null,"Rocky","Perro",5,"Perro protector y muy energético","Disponible","Concepción",new Date());

                Mascota masc4 = new Mascota(null,"Nala","Gato",1,"Gatita curiosa y muy juguetona","Disponible","La Serena",new Date());

                mascotaRepo.save(masc1);
                mascotaRepo.save(masc2);
                mascotaRepo.save(masc3);
                mascotaRepo.save(masc4);

                System.out.println("Base de datos cargada");
            }

        };
    }
}
