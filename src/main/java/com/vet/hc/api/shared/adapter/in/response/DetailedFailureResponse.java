package com.vet.hc.api.shared.adapter.in.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a detailed failure response.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DetailedFailureResponse {
    private String message;
    private List<Detail> details;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private String field;
        private List<String> messages;
    }
}
