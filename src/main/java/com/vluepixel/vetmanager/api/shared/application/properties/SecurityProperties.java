package com.vluepixel.vetmanager.api.shared.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.vluepixel.vetmanager.api.shared.application.annotations.Properties;

import lombok.Getter;
import lombok.Setter;

/**
 * Security properties.
 */
@Getter
@Setter
@Properties
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private boolean allowCredentials;
    private String[] allowedOrigins;
    private String[] allowedHeaders;
    private String[] exposedHeaders;
    private String[] allowedMethods;
    private String[] allowedPublicRoutes;
}