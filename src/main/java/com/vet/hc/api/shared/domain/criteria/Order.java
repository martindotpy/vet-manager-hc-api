package com.vet.hc.api.shared.domain.criteria;

import static com.vet.hc.api.shared.domain.util.CaseConverterUtil.toCamelCase;

import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

/**
 * Order class.
 */
@Getter
@ToString
public final class Order {
    private final OrderType type;
    private final String field;

    private Order(OrderType type, String field) {
        Objects.requireNonNull(type, "Type is required");
        Objects.requireNonNull(field, "Field is required");

        this.type = type;
        this.field = toCamelCase(field);
    }

    private Order(OrderType type) {
        this.type = type;
        this.field = null;
    }

    public static Order of(String type, String field) {
        return new Order(OrderType.valueOf(type.toUpperCase()), field);
    }

    public static Order of(OrderType type, String field) {
        return new Order(type, field);
    }

    public static Order asc(String field) {
        return new Order(OrderType.ASC, field);
    }

    public static Order desc(String field) {
        return new Order(OrderType.DESC, field);
    }

    public static Order none() {
        return new Order(OrderType.NONE);
    }
}
