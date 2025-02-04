package com.vet.hc.api.user.core.adapter.in.request;

import com.vet.hc.api.user.core.domain.payload.UpdateCurrentUserPayload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update current user request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateCurrentUserRequest implements UpdateCurrentUserPayload {
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name is too long")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name is too long")
    private String lastName;
}
