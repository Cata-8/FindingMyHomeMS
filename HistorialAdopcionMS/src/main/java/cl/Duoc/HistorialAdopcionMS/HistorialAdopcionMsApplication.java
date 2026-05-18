package cl.Duoc.HistorialAdopcionMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HistorialAdopcionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistorialAdopcionMsApplication.class, args);

		System.out.println("www");
	}

}
