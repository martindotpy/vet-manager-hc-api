package com.vet.hc.api.shared.domain.query;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paginated result.
 */
@Getter
@SuperBuilder
public class PaginatedResult<T> extends Response<T> {
    private final int page;
    private final int pageSize;
    private final int totalPages;
    private final long totalElements;
}
