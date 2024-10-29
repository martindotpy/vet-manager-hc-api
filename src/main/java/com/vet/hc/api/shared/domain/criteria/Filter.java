package com.vet.hc.api.shared.domain.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Filter class.
 */
@Getter
@AllArgsConstructor
public final class Filter {
    private final String field;
    private final FilterOperator operator;
    private final String value;
}
