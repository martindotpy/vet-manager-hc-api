package com.vet.hc.api.shared.adapter.in.response;

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
