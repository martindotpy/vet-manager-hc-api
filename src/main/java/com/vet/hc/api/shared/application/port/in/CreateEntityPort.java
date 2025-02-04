package com.vet.hc.api.shared.application.port.in;

import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.payload.Payload;
import com.vet.hc.api.shared.domain.result.Result;

/**
 * Create entity port.
 */
public interface CreateEntityPort<DTO, F extends Failure, P extends Payload> {
    /**
     * Create entity.
     *
     * @param payload the payload.
     * @return the result of the creation. The dto if the entity was created
     *         successfully, the failure otherwise.
     */
    Result<DTO, F> create(P payload);
}
