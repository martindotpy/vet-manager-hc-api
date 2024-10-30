package com.vet.hc.api.shared.domain.criteria;

import static com.google.common.base.Preconditions.checkArgument;

import lombok.Getter;

/**
 * Order class.
 */
@Getter
public final class Order {
    private String field;
    private OrderType type;

    private Order(String field, OrderType type) {
        this.field = field;
        this.type = type;
    }

    public static Order of(String field, OrderType type) {
        type = type != null ? type : OrderType.NONE;

        checkArgument(type != OrderType.NONE && field == null, "Field is required when type is not NONE");

        return new Order(field, type);
    }
}
