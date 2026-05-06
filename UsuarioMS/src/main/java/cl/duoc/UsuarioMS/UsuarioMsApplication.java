package cl.duoc.UsuarioMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UsuarioMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioMsApplication.class, args);

		System.out.println("www");
	}

}
