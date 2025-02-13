package com.vluepixel.vetmanager.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vluepixel.vetmanager.api.shared.adapter.in.validation.JakartaValidator;
import com.vluepixel.vetmanager.api.shared.domain.validation.ExternalRequestValidatorProvider;

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
        SpringApplication.run(VetManagerHCApiApplication.class, args);
    }

    private final Validator validator;

    @Override
    public void run(String... args) {
        // Add external request validation
        if (ExternalRequestValidatorProvider.get() == null)
            ExternalRequestValidatorProvider.set(new JakartaValidator(validator));

        else
            log.warn("External request validation provider already set");
    }
}
