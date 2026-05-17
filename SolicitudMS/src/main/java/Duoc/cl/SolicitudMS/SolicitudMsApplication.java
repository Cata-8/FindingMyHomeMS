package Duoc.cl.SolicitudMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@FeignClient
public class SolicitudMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolicitudMsApplication.class, args);
	}

}
