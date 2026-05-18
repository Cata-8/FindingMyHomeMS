package cl.Duoc.HistorialAdopcionMS;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootTest
@EnableFeignClients
class HistorialAdopcionMsApplication{

	public static void main(String[] args) {
		SpringApplication.run(HistorialAdopcionMsApplication.class, args);

		System.out.println("www");


	} 

}
