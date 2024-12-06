package com.vet.hc.api.auth.core.adapter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.vet.hc.api.auth.core.adapter.annotations.Properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Properties
@ConfigurationProperties(prefix = "security")
public final class SecurityProperties {
    private String[] allowedMethods;
    private String[] allowedHeaders;
    private String[] allowedPublicRoutes;
    private String[] allowedOrigins;
    private boolean allowedCredentials;
}
