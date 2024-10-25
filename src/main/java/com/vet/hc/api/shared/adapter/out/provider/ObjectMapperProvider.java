package com.vet.hc.api.shared.adapter.out.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vet.hc.api.shared.adapter.out.bean.CustomObjectMapper;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 * Custom ObjectMapper provider for Jackson.
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return new CustomObjectMapper();
    }
}
