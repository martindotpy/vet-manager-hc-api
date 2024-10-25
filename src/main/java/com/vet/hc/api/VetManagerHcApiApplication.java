package com.vet.hc.api;

import com.vet.hc.api.shared.adapter.out.bean.CustomModelResolver;

import io.swagger.v3.core.converter.ModelConverters;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configures the base path for the REST API.
 */
@ApplicationPath("api/v0")
public class VetManagerHCApiApplication extends Application {
    // Configs
    public VetManagerHCApiApplication() {
        // Configures the custom model resolver for the Swagger documentation
        ModelConverters.getInstance().addConverter(new CustomModelResolver());
    }
}
