package com.vet.hc.api.shared.adapter.out.config;

import java.util.Properties;
import java.util.Set;

import lombok.Getter;

@Getter
public class ApplicationProperties {
    private String securityJwtPassword;
    private Long securityJwtExpiration;
    private Set<String> securityApiPublicEndpoints;

    public ApplicationProperties() {
        Properties properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        securityJwtPassword = properties.getProperty("security.jwt.secret");
        securityJwtExpiration = Long.parseLong(properties.getProperty("security.jwt.expiration"));
        securityApiPublicEndpoints = Set.of(properties.getProperty("security.api.public-endpoints").split(","));
    }
}
