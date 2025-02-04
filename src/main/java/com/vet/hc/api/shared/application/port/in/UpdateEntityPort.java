package com.vet.hc.api.shared.application.port.in;

import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.payload.Payload;
import com.vet.hc.api.shared.domain.result.Result;

/**
 * Update entity port.
 */
public interface UpdateEntityPort<DTO, F extends Failure, P extends Payload> {
    /**
     * Update entity.
     *
     * @param payload the payload.
     * @return the result of the update. The dto if the entity was updated
     *         successfully, the failure otherwise.
     */
    Result<DTO, F> update(P payload);
}
