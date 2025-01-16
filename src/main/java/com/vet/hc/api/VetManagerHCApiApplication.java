package com.vet.hc.api;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vet.hc.api.shared.adapter.in.validation.JakartaValidator;
import com.vet.hc.api.shared.domain.validation.ExternalPayloadValidatorProvider;
import com.vet.hc.api.user.core.domain.enums.UserRole;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.model.UserImpl;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Configures the base path for the REST API.
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class VetManagerHCApiApplication implements CommandLineRunner {
    public static void main(String[] args) {
        System.setProperty("user.timezone", "America/Lima");

        SpringApplication.run(VetManagerHCApiApplication.class, args);
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public void run(String... args) {
        // Add external payload validation
        ExternalPayloadValidatorProvider.set(new JakartaValidator(validator));

        // Add the admin user if it does not exist
        if (!userRepository.adminExists()) {
            User user = UserImpl.builder()
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
