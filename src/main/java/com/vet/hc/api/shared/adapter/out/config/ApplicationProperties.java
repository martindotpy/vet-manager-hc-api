package com.vet.hc.api.shared.adapter.out.config;

import java.util.Properties;
import java.util.Set;

import lombok.Getter;

/**
 * Application properties.
 */
@Getter
public class ApplicationProperties {
    private String securityJwtPassword;
    private Long securityJwtExpiration;
    private Set<String> securityApiPublicEndpoints;
    private Set<String> securityCorsAllowedOrigins;
    private Set<String> securityCorsAllowedMethods;
    private Set<String> securityCorsAllowedHeaders;

    public ApplicationProperties() {
        Properties properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        securityJwtPassword = properties.getProperty("security.jwt.secret");
        securityJwtExpiration = Long.parseLong(properties.getProperty("security.jwt.expiration"));
        securityApiPublicEndpoints = loadSet(properties.getProperty("security.api.public-endpoints"));
        securityCorsAllowedOrigins = loadSet(properties.getProperty("security.cors.allowed-origins"));
        securityCorsAllowedMethods = loadSet(properties.getProperty("security.cors.allowed-methods"));
        securityCorsAllowedHeaders = loadSet(properties.getProperty("security.cors.allowed-headers"));
    }

    /**
     * Load a set of strings from a property.
     *
     * @param property the property to load the set from.
     * @return the set of strings
     */
    private Set<String> loadSet(String property) {
        return Set.of(property.trim().replace(" ", "").split(","));
    }
}
