package cl.sonidovivo.noticias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI noticiasOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Sonido Vivo - Servicio de Noticias")
                        .description("Publicaciones públicas; crear, editar y eliminar requiere token de administrador.")
                        .version("1.0"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
