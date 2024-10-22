package com.vet.hc.api.user.adapter.out.bean;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

/**
 * Custom object mapper for mapping the objects to JSON and vice versa.
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        this.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.setSerializationInclusion(Include.CUSTOM);
    }
}
