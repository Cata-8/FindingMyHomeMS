package cl.duoc.publicacionMS.config;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.publicacionMS.model.Publicacion;
import cl.duoc.publicacionMS.repository.PublicacionRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(PublicacionRepository publicacionRepo){
        return args ->{
            if (publicacionRepo.count() > 0) {
                System.out.println("No se cargó nada poruqe ya hay datos");
            } else {
                Publicacion publi1 = new Publicacion(null,"Perro en adopción","Perro de 3 años cariñoso busca hogar", new Date(), "Activo",1);
                Publicacion publi2 = new Publicacion(null,"Gato en adopción","Gato adulto tranquilo y con todas sus vacunas busca familia", new Date(),"Pausado",2);
                Publicacion publi3 = new Publicacion(null,"Gatita en adopción","Gata de 4 meses busca familia responsable", new Date(), "Cerrado",4);

                publicacionRepo.save(publi1);
                publicacionRepo.save(publi2);
                publicacionRepo.save(publi3);
                
                System.out.println("Base de datos cargada");
            }
        };
    }
}
