package com.inditex.zara.api.infrastructure.adapters.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inditex - Zara Pricing API")
                        .version("1.0")
                        .description("API para consultar el precio aplicable de un producto según fecha, marca y tarifa.")
                );
    }
}
