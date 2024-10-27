package com.vet.hc.api;

import com.vet.hc.api.shared.adapter.out.bean.CustomModelResolver;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.HttpHeaders;

/**
 * Configures the base path for the REST API.
 */
@OpenAPIDefinition(info = @Info(title = "Vet Manager HC API", version = "0.0.1", description = "API for the Vet Manager HC application."), servers = {
        @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080", description = "Local server")
}, security = {
        @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "Security Token")
})
@SecurityScheme(name = "Security Token", description = "Bearer token for authentication. Log in or register in the application if you do not have one.", type = SecuritySchemeType.HTTP, paramName = HttpHeaders.AUTHORIZATION, in = SecuritySchemeIn.HEADER, scheme = "Bearer", bearerFormat = "JWT")
@ApplicationPath("api/v0")
public class VetManagerHCApiApplication extends Application {
    // Configs
    public VetManagerHCApiApplication() {
        // Configures the custom model resolver for the Swagger documentation
        ModelConverters.getInstance().addConverter(new CustomModelResolver());
    }
}
