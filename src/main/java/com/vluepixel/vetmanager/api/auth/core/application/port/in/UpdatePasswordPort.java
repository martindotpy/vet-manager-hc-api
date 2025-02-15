package com.vluepixel.vetmanager.api.auth.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.domain.request.UpdatePasswordRequest;

/**
 * Update password port.
 */
public interface UpdatePasswordPort {
    /**
     * Update password.
     *
     * @param request the request with the password and new password.
     */
    void update(UpdatePasswordRequest request);
}
