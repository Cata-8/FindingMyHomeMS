package cl.duoc.fichaSaludMS.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.fichaSaludMS.model.FichaSalud;
import cl.duoc.fichaSaludMS.model.RegistroVacuna;
import cl.duoc.fichaSaludMS.model.Vacuna;
import cl.duoc.fichaSaludMS.repository.FichaSaludRepository;
import cl.duoc.fichaSaludMS.repository.RegistroVacunaRepository;
import cl.duoc.fichaSaludMS.repository.VacunaRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(FichaSaludRepository fichaSaludRepo, RegistroVacunaRepository registroVacunaRepo, VacunaRepository vacunaRepo){
        return args -> {
            if (vacunaRepo.count() > 0) {
                System.out.println("No se cargó nada porque ya habían datos");
            } else {

                
                Vacuna vacuna1 = new Vacuna();
            vacuna1.setNombre("Rabia");

            Vacuna vacuna2 = new Vacuna();
            vacuna2.setNombre("Séxtuple");

            vacunaRepo.save(vacuna1);
            vacunaRepo.save(vacuna2);

            FichaSalud ficha = new FichaSalud();

            ficha.setDesparasitado(true);
            ficha.setEnfermedades("ninguna");
            ficha.setEstadoReproductivo("esterilizado");
            ficha.setMicroship(false);

            ficha.setIdMascota(1);

            List<RegistroVacuna> registros = new ArrayList<>();

            RegistroVacuna registro1 = new RegistroVacuna();

            registro1.setVacuna(vacuna1);
            registro1.setDosis(1);
            registro1.setFecha(new Date());

            registro1.setFichaSalud(ficha);

            registros.add(registro1);

            RegistroVacuna registro2 = new RegistroVacuna();

            registro2.setVacuna(vacuna2);
            registro2.setDosis(2);
            registro2.setFecha(new Date());

            registro2.setFichaSalud(ficha);

            registros.add(registro2);

            ficha.setRegistroVacunas(registros);

            fichaSaludRepo.save(ficha);

            System.out.println("Datos cargados correctamente");
                

            }
        };
    }

}
