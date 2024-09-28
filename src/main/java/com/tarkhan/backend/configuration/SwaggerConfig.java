package com.tarkhan.backend.configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BookWave REST API Documentation",
                version = "1.0",
                description = "BookWave REST API Documentation",
                contact = @Contact(
                        name = "Tarkhan Gurbanli",
                        email = "tarkhangurbanli@gmail.com",
                        url = "https://www.bookwave.com"
                )
        ),
        security = {@SecurityRequirement(name = "bearerToken")},
        externalDocs = @ExternalDocumentation(
                description = "BookWave REST API Documentation",
                url = "http://localhost:9090/swagger-ui/index.html"
        )
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerToken",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})
public class SwaggerConfig {
}
