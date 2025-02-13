package com.vluepixel.vetmanager.api.shared.domain.criteria;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Getter;

/**
 * Criteria class.
 */
@Getter
public sealed class Criteria permits OrderedCriteria {
    private final Collection<Filter> filters;

    protected Criteria(Collection<Filter> filters) {
        this.filters = filters;
    }

    /**
     * Creates a new criteria instance.
     *
     * @param necessaryFilter Necessary filter.
     * @param filters         Filters.
     * @return Criteria instance.
     * @throws NullPointerException If any filter is null.
     */
    public static Criteria of(Filter necessaryFilter, Filter... filters) {
        var allFilters = new CopyOnWriteArrayList<>(filters);
        allFilters.add(0, necessaryFilter);

        for (var filter : allFilters) {
            Objects.requireNonNull(filter, "Filter cannot be null");
        }

        return new Criteria(allFilters);
    }

    /**
     * Creates a new criteria instance.
     *
     * @param filters Filters.
     * @return Criteria instance.
     * @throws IllegalArgumentException If filters is empty.
     */
    public static Criteria of(Collection<Filter> filters) {
        if (filters.isEmpty()) {
            throw new IllegalArgumentException("Filters cannot be empty");
        }

        for (var filter : filters) {
            Objects.requireNonNull(filter, "Filter cannot be null");
        }

        return new Criteria(new CopyOnWriteArrayList<>(filters));
    }
}
