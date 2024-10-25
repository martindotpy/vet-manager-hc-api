package com.vet.hc.api.shared.adapter.out.bean;

import io.swagger.v3.core.jackson.ModelResolver;

/**
 * Custom model resolver for the Swagger documentation.
 */
public class CustomModelResolver extends ModelResolver {
    public CustomModelResolver() {
        super(new CustomObjectMapper());
    }
}
