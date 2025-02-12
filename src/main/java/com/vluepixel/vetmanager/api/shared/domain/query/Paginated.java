package com.vluepixel.vetmanager.api.shared.domain.query;

import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paginated response.
 */
@Getter
@SuperBuilder
public class Paginated<T> {
    private final int page;
    private final int size;
    private final int totalPages;
    private final long totalElements;
    private final List<T> content;

    public <M> Paginated<M> map(Function<T, M> mapper) {
        return Paginated.<M>builder()
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .content(content.stream().map(mapper).toList())
                .build();
    }
}
