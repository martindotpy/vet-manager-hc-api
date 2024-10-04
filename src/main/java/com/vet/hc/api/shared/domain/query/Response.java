package com.vet.hc.api.shared.domain.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Abstract class for responses.
 */
@SuperBuilder
@Getter
@Hidden
public abstract class Response {
    private String message;
    @JsonIgnore
    private Integer httpStatus;
}
