package com.vet.hc.api.shared.application.adapter.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a response.
 */
@Getter
@SuperBuilder
public class ContentResponse<T> {
    private final String message;
    private final T content;
}
