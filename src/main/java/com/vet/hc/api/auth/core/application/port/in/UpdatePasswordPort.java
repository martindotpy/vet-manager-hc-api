package com.vet.hc.api.auth.core.application.port.in;

import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.UpdatePasswordPayload;
import com.vet.hc.api.shared.domain.result.Result;

/**
 * Update password port.
 */
public interface UpdatePasswordPort {
    /**
     * Update password.
     *
     * @param payload the payload with the password and new password.
     * @return the result of the operation
     */
    Result<Void, AuthFailure> update(UpdatePasswordPayload payload);
}
