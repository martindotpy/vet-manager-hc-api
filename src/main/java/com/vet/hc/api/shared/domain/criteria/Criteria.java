package com.vet.hc.api.shared.domain.criteria;

import java.util.List;

import lombok.Getter;

/**
 * Criteria class.
 */
@Getter
public final class Criteria {
    private final List<Filter> filters;
    private final Order order;
    private final Integer size;
    private final Integer page;

    public Criteria(List<Filter> filters, Order order, Integer size, Integer page) {
        this.filters = filters;
        this.order = order;
        this.size = size;
        this.page = page;
    }
}
