package com.vet.hc.api.shared.application.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Create payload mapper.
 */
public interface CreatePayloadMapper<P extends Payload, T> {
    /**
     * Map payload to entity.
     *
     * <p>
     * Will ignore all unmapped properties of the entity.
     * </p>
     *
     * @param payload the create entity payload.
     * @return the entity.
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    T fromCreate(P payload);
}
