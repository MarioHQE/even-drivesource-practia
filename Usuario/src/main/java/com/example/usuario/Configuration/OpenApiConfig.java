package com.example.usuario.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usuarioOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Usuario API").version("v1").description("API del servicio Usuario"));
    }

}
