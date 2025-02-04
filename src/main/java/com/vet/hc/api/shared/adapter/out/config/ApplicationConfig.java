package com.vet.hc.api.shared.adapter.out.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vet.hc.api.shared.adapter.out.custom.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuration class for the application.
 */
@Configuration
public class ApplicationConfig {
    /**
     * Bean for ObjectMapper.
     *
     * <p>
     * In this case, the ObjectMapper is configured with the following properties:
     * <ul>
     * <li>PropertyNamingStrategy: SNAKE_CASE</li>
     * <li>SerializationInclusion: CUSTOM</li>
     * </ul>
     *
     *
     * Helps to convert Java objects to JSON and vice versa.
     * </p>
     *
     * @return A new instance of ObjectMapper
     */
    @Bean
    ObjectMapper objectMapper() {
        return new CustomObjectMapper();
    }
}
