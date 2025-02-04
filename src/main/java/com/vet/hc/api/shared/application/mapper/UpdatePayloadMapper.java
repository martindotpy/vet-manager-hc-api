package com.vet.hc.api.shared.application.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Update payload mapper.
 */
public interface UpdatePayloadMapper<P extends Payload, B> {
    /**
     * Map payload to entity.
     *
     * <p>
     * Will ignore all unmapped properties of the entity.
     * </p>
     *
     * @param payload the update entity payload.
     * @return the entity.
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    B fromUpdate(P payload);
}
