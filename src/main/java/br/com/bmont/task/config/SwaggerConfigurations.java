package br.com.bmont.task.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigurations {
    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("Task")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI taskOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Task API")
                        .description("This service is intended to manage tasks")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html)"))
                        .contact(new Contact().name("Junior Monteiro").email("juniorbmonteiro@hotmail.com").url("https://linkedin.com/in/juniorbmonteiro")))
                .externalDocs(new ExternalDocumentation().description("Learn more about this project").url("https://github.com/JuniorBMonteiro/task-api"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .in(SecurityScheme.In.HEADER)
                                .bearerFormat("JWT")))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-key"));
    }
}
