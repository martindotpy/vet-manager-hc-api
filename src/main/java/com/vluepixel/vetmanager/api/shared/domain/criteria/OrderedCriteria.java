package com.vluepixel.vetmanager.api.shared.domain.criteria;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Getter;

/**
 * Ordered criteria class.
 */
@Getter
public sealed class OrderedCriteria extends Criteria permits PaginatedCriteria {
    private final Order order;

    protected OrderedCriteria(Order order, Collection<Filter> filters) {
        super(filters);
        this.order = order;
    }

    /**
     * Creates a new ordered criteria instance.
     *
     * @param order           Order.
     * @param necessaryFilter Necessary filter.
     * @param filters         Filters.
     * @return Ordered criteria instance.
     * @throws NullPointerException If any filter is null.
     */
    public static OrderedCriteria of(Order order, Filter necessaryFilter, Filter... filters) {
        Objects.requireNonNull(order, "Order cannot be null");

        var allFilters = new CopyOnWriteArrayList<>(filters);
        allFilters.add(0, necessaryFilter);

        for (var filter : allFilters) {
            Objects.requireNonNull(filter, "Filter cannot be null");
        }

        return new OrderedCriteria(order, allFilters);
    }

    /**
     * Creates a new ordered criteria instance.
     *
     * @param order   Order.
     * @param filters Filters.
     * @return Ordered criteria instance.
     * @throws IllegalArgumentException If filters is empty.
     * @throws NullPointerException     If any filter is null.
     */
    public static OrderedCriteria of(Order order, Collection<Filter> filters) {
        Objects.requireNonNull(order, "Order cannot be null");
        Objects.requireNonNull(filters, "Filters cannot be null");

        if (filters.isEmpty()) {
            throw new IllegalArgumentException("Filters cannot be empty");
        }

        for (var filter : filters) {
            Objects.requireNonNull(filter, "Filter cannot be null");
        }

        return new OrderedCriteria(order, new CopyOnWriteArrayList<>(filters));
    }
}
