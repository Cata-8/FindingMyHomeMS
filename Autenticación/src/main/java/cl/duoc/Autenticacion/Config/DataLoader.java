package cl.duoc.Autenticacion.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Autenticacion.model.Autenticacion;
import cl.duoc.Autenticacion.repository.AutenticacionRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(AutenticacionRepository authRepository) {
        return args -> {
            Autenticacion auth1 = new Autenticacion(null, 1, null, "activo");
            Autenticacion auth2 = new Autenticacion(null, 2, null, "activo");
            Autenticacion auth3 = new Autenticacion(null, 3, null, "activo");
            Autenticacion auth4 = new Autenticacion(null, 4, null, "activo");

            authRepository.save(auth1);
            authRepository.save(auth2);
            authRepository.save(auth3);
            authRepository.save(auth4);

            System.out.println("Datos de autenticación cargados correctamente");
        };
    }
}
