package com.vet.hc.api.shared.domain.query;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paginated response.
 */
@Getter
@SuperBuilder
public class PaginatedResponse<T> extends ContentResponse<T> {
    private final int page;
    private final int size;
    private final int totalPages;
    private final long totalElements;
}
