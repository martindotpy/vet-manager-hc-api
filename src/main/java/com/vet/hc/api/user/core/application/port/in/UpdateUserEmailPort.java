package com.vet.hc.api.user.core.application.port.in;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.payload.UpdateUserEmailPayload;

/**
 * Update email port.
 */
public interface UpdateUserEmailPort {
    /**
     * Update email.
     *
     * @param payload the payload.
     * @return the result
     */
    Result<UserDto, UserFailure> update(UpdateUserEmailPayload payload);

    /**
     * Update current user.
     *
     * @param payload the payload.
     * @return the result
     */
    Result<JwtDto, UserFailure> updateCurrentUser(UpdateUserEmailPayload payload);
}
