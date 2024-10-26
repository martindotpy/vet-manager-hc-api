package com.vet.hc.api.shared.domain.query;

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
public class FailureResponse {
    private String message;
}
