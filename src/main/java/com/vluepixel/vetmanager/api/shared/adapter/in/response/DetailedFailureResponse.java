package com.vluepixel.vetmanager.api.shared.adapter.in.response;

import static com.vluepixel.vetmanager.api.shared.domain.util.CaseConverterUtils.toSnakeCase;

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

        public String getField() {
            return toSnakeCase(field);
        }
    }
}
