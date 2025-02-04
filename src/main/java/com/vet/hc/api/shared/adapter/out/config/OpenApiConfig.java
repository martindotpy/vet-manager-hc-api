package com.vet.hc.api.shared.adapter.out.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;

@SecurityScheme(name = "Security Token", description = "Bearer token for authentication. Login to the application if you do not have one.", type = SecuritySchemeType.HTTP, paramName = HttpHeaders.AUTHORIZATION, in = SecuritySchemeIn.HEADER, scheme = "Bearer", bearerFormat = "JWT")
@Configuration
public class OpenApiConfig {
    @Value("${springdoc.api-docs.server-url}")
    private String serverUrl;

    /**
     * Bean for OpenAPI.
     *
     * <p>
     * In this case, the OpenAPI is configured with the title, version, description,
     * servers, and security requirements. The server is injected from the
     * application properties.
     * </p>
     *
     * @return A new instance of OpenAPI
     */
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vet Manager Hc API")
                        .version("0.0.1-SNAPSHOT")
                        .description(
                                "API for Vet Manager HC. This API is used to manage the veterinary clinic."))
                .servers(
                        List.of(new Server()
                                .url(serverUrl)
                                .description("Server provided")))
                .addSecurityItem(new SecurityRequirement().addList("Security Token"));
    }

    /**
     * Bean for ModelResolver.
     *
     * <p>
     * In this case, the ModelResolver is configured with the object mapper
     * defined in the application context (injected by Spring).
     * </p>
     *
     * @param objectMapper The object mapper
     * @return A new instance of ModelResolver
     */
    @Bean
    ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}
