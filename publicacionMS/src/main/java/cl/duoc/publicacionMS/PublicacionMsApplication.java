package cl.duoc.publicacionMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PublicacionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicacionMsApplication.class, args);
		System.out.println("hola");
	}

}
