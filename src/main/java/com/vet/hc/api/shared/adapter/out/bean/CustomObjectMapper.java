package com.vet.hc.api.shared.adapter.out.bean;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Custom object mapper for mapping the objects to JSON and vice versa.
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        this.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.setSerializationInclusion(Include.CUSTOM);
        this.findAndRegisterModules();
        this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
