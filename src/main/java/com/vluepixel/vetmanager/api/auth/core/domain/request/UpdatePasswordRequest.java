package com.vluepixel.vetmanager.api.auth.core.domain.request;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update password request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdatePasswordRequest implements Request {
    private String password;
    private String newPassword;
}
