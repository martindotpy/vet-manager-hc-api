package com.vet.hc.api.user.core.application.port.in;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.shared.application.port.in.UpdateEntityPort;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.payload.UpdateUserProfileImagePayload;

/**
 * Update user profile image port.
 */
public interface UpdateUserProfileImagePort
        extends UpdateEntityPort<UserDto, UserFailure, UpdateUserProfileImagePayload> {
    /**
     * Update the current user.
     *
     * @param payload the payload.
     * @return the result
     */
    Result<JwtDto, UserFailure> updateCurrentUser(UpdateUserProfileImagePayload payload);
}
