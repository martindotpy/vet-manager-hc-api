package com.vet.hc.api.shared.domain.criteria;

import java.util.Collection;

import lombok.Getter;

@Getter
public final class LogicalFilter extends Filter {
    private final LogicalOperator logicalOperator;
    private final Filter[] filters;

    protected LogicalFilter(LogicalOperator logicalOperator, Filter necessaryFilter, Filter... filters) {
        super();
        this.logicalOperator = logicalOperator;
        this.filters = new Filter[filters.length + 1];
        this.filters[0] = necessaryFilter;
        System.arraycopy(filters, 0, this.filters, 1, filters.length);
    }

    protected LogicalFilter(LogicalOperator logicalOperator, Collection<Filter> filters) {
        super();

        if (filters.isEmpty()) {
            throw new IllegalArgumentException("Filters cannot be empty");
        }

        this.logicalOperator = logicalOperator;
        this.filters = filters.toArray(new Filter[0]);
    }
}
