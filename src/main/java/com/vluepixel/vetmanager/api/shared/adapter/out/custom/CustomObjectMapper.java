package com.vluepixel.vetmanager.api.shared.adapter.out.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Custom object mapper.
 */
public final class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        super();

        this.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.findAndRegisterModules();
        this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
