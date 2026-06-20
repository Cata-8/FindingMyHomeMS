package cl.duoc.mensajeriaMS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Microservicio gestión de mensajería")
                .version("1.0")
                .description("Documentación api que permite la gestión de mensajes entre usuarios")
            );
    }

}
