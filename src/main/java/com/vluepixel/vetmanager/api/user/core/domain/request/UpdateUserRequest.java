package com.vluepixel.vetmanager.api.user.core.domain.request;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update user request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateUserRequest implements Request {
    @NotNull(message = "Id is required")
    @Positive(message = "Id must be positive")
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name is too long")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name is too long")
    private String lastName;
}
