package com.vet.hc.api.auth.core.adapter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vet.hc.api.auth.core.adapter.properties.SecurityProperties;

import lombok.RequiredArgsConstructor;

/**
 * Cors configuration class.
 */
@Configuration
@RequiredArgsConstructor
public class CorsConfigurer implements WebMvcConfigurer {
    private final SecurityProperties securityProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(securityProperties.getAllowedOrigins())
                .allowedMethods(securityProperties.getAllowedMethods())
                .allowedHeaders(securityProperties.getAllowedHeaders())
                .allowCredentials(securityProperties.isAllowedCredentials());
    }
}
