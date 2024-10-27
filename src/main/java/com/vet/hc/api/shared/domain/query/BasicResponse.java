package com.vet.hc.api.shared.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a basic response.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponse {
    private String message;
}
