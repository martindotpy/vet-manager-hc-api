package com.vet.hc.api.shared.domain.criteria;

import static com.google.common.base.Preconditions.checkArgument;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Order class.
 */
@Slf4j
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

        log.info("Creating order with field: {} and type: {}", field, type);
        checkArgument(!(type != OrderType.NONE && field == null), "Field is required when type is not NONE");

        return new Order(field, type);
    }
}
