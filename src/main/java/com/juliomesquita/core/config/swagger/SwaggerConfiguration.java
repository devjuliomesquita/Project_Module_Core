package com.juliomesquita.core.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {
   @Bean
   public OpenAPI openAPI() {
      final String securitySchemeName = "bearerAuth";
      return new OpenAPI()
              .info(new Info().
                      title("Project Core")
                      .summary("Está API é um setup inicial para projetos modulares.")
                      .description("Está API é um setup inicial para projetos modulares.")
                      .version("1.0")
                      .termsOfService("juliocesarmcamilo@gmail.com")
                      .contact(new Contact().name("Júlio Mesquita").email("juliocesarmcamilo@gmail.com").url("juliocesarmcamilo@gmail.com"))
                      .license(new License().name("Júlio Mesquita - Licensa MIT").url("juliocesarmcamilo@gmail.com")))
              .servers(List.of(new Server().description("Ambiente LOCAL").url("http://localhost:8080/api")))
              .components(new Components()
                      .addSecuritySchemes(securitySchemeName,
                              new SecurityScheme()
                                      .name(securitySchemeName)
                                      .type(SecurityScheme.Type.HTTP)
                                      .scheme("bearer")
                                      .bearerFormat("JWT")
                      )
              )
              ;
   }
}
