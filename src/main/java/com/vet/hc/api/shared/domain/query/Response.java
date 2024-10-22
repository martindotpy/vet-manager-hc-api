package com.vet.hc.api.shared.domain.query;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Response<T> {
    private final String message;
    private final T content;
}
