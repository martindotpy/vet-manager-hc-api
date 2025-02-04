package com.vet.hc.api.shared.application.port.in;

import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.result.Result;

/**
 * Delete entity port.
 */
public interface DeleteEntityPort<F extends Failure, ID> {
    /**
     * Delete entity by id.
     *
     * @param id the entity id.
     * @return the result of the operation. Void if successful, failure otherwise.
     */
    Result<Void, F> deleteById(ID id);
}
