package com.uniloftsky.springframework.spring5freelancedeliveryservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Freelance delivery service API")
                        .description("Spring Boot application what provides API for diploma work: 'Freelance Delivery Service'.")
                        .version("v1")
                        .contact(new Contact()
                                .email("uniloftsky@gmail.com")
                                .name("Anton")));
    }

}
