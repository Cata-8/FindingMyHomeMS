package cl.duoc.donacionMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients

@SpringBootApplication
public class DonacionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonacionMsApplication.class, args);
		System.out.println("hola");
	}

}
