package cl.duoc.fichaSaludMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FichaSaludMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FichaSaludMsApplication.class, args);
	}

}
