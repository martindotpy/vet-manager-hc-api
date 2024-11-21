package com.vet.hc.api;

import java.util.Set;

import com.vet.hc.api.auth.core.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.shared.adapter.in.validation.JakartaValidator;
import com.vet.hc.api.shared.adapter.out.bean.CustomModelResolver;
import com.vet.hc.api.shared.domain.validation.ExternalPayloadValidatorProvider;
import com.vet.hc.api.user.core.domain.enums.UserRole;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

/**
 * Configures the base path for the REST API.
 */
@Slf4j
@OpenAPIDefinition(info = @Info(title = "Vet Manager HC API", version = "0.0.1-SNAPSHOT", description = "API for the Vet Manager HC application."), servers = {
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

    @Inject
    private UserRepository userRepository;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private Validator validator;

    @PostConstruct
    public void init() {
        // Add external payload validation
        ExternalPayloadValidatorProvider.set(new JakartaValidator(validator));
        log.info("Jakarta validation provider set");

        // Add the admin user if it does not exist
        if (!userRepository.adminExists()) {
            User user = User.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(UserRole.ADMIN))
                    .build();

            userRepository.save(user);

            log.info("Admin user created");
        }
    }
}
