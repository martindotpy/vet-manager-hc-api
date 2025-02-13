package com.vluepixel.vetmanager.api.shared.domain.criteria;

import static com.vluepixel.vetmanager.api.shared.domain.util.CaseConverterUtils.toCamelCase;

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
        if (type == null)
            this.type = OrderType.NONE;
        else
            this.type = type;

        if (type != null)
            this.field = toCamelCase(field);
        else
            this.field = null;
    }

    private Order(OrderType type) {
        this(type, null);
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
