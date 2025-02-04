package com.vet.hc.api.shared.domain.criteria;

import static com.vet.hc.api.shared.domain.util.CaseConverterUtil.toCamelCase;

import java.util.Objects;

import lombok.Getter;

@Getter
public final class ValueFilter extends Filter {
    private final String field;
    private final FilterOperator filterOperator;
    private Object value;

    protected ValueFilter(String field, FilterOperator operator, Object value) {
        super();
        Objects.requireNonNull(field, "Field is required");
        Objects.requireNonNull(operator, "Operator is required");

        this.field = toCamelCase(field);
        this.filterOperator = operator;
        this.value = value;
    }

    protected ValueFilter(String field, FilterOperator operator) {
        super();
        Objects.requireNonNull(field, "Field is required");
        Objects.requireNonNull(operator, "Operator is required");

        this.field = toCamelCase(field);
        this.filterOperator = operator;
    }
}
