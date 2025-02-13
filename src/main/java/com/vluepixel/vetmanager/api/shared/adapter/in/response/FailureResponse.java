package com.vluepixel.vetmanager.api.shared.adapter.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a basic failure response.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FailureResponse {
    private String message;
}
