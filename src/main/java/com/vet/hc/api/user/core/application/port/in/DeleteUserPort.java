package com.vet.hc.api.user.core.application.port.in;

import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.domain.failure.UserFailure;

/**
 * Delete user port.
 */
public interface DeleteUserPort {
    /**
     * Delete a user.
     *
     * @param id the id of the user to delete.
     * @return the result of the operation.
     */
    Result<Void, UserFailure> deleteById(Long id);
}
