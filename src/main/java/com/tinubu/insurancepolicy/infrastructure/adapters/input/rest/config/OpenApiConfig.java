package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI insurancePolicyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Insurance Policy API")
                        .description("This API provides endpoints to manage insurance policies including creating, retrieving, and updating policies.")
                        .version("1.0.0")
                );
    }
}
