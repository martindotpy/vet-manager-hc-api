package com.vet.hc.api.shared.adapter.in.converter;

import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.domain.criteria.OrderType;

@Component
public class StringToOrderType extends StringToEnumConverter<OrderType> {
    public StringToOrderType() {
        super(OrderType.class);
    }
}
