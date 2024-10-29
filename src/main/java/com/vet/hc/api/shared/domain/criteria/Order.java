package com.vet.hc.api.shared.domain.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Order class.
 */
@Getter
@AllArgsConstructor
public final class Order {
    private String field;
    private OrderType type;
}
