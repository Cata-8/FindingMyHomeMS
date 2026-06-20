package cl.duoc.UsuarioMS.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    public OpenAPI customOpenAPI(){
        return new OpenAPI()
        .info(new Info()
            .title("Microservicio gestión de usuario")
            .version("2.1")
            .description("Documentación api que permite la gestión")
        );
    }

}
