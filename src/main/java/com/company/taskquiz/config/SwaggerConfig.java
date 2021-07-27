package com.company.taskquiz.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration

public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().
                components(new Components().addSecuritySchemes("basic-scheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))).
                addSecurityItem(new SecurityRequirement().addList("basic-scheme")).
                info(new Info().title("Api"));
    }
}
