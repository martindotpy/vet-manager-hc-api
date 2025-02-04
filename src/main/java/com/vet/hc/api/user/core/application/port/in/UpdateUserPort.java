package com.vet.hc.api.user.core.application.port.in;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.shared.application.port.in.UpdateEntityPort;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.payload.UpdateUserPayload;

/**
 * Update user port.
 */
public interface UpdateUserPort extends UpdateEntityPort<UserDto, UserFailure, UpdateUserPayload> {
    /**
     * Update the current user.
     *
     * @param payload the payload.
     * @return the result
     */
    Result<JwtDto, UserFailure> updateCurrentUser(UpdateUserPayload payload);
}
