package com.vluepixel.vetmanager.api.shared.adapter.in.response;

import java.util.List;

import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paginated response.
 */
@Getter
@SuperBuilder
public class PaginatedResponse<T> {
    private final int page;
    private final int size;
    private final int totalPages;
    private final long totalElements;
    private final String message;
    private final List<T> content;

    /**
     * Creates a paginated response from a paginated object.
     *
     * @param paginated the paginated object
     * @param <T>       the type of the content
     * @return the paginated response
     */
    public static <T> PaginatedResponse<T> from(Paginated<T> paginated, String message) {
        return PaginatedResponse.<T>builder()
                .page(paginated.getPage())
                .size(paginated.getSize())
                .totalPages(paginated.getTotalPages())
                .totalElements(paginated.getTotalElements())
                .content(paginated.getContent())
                .message(message)
                .build();
    }
}
