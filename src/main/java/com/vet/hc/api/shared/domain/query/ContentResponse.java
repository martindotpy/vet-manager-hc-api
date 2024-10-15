package com.vet.hc.api.shared.domain.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Abstract class for a response with content.
 *
 * <p>
 * This is the base class for all responses that have content. It is used to
 * indicate that the operation was successful and that there is content to be
 * returned.
 * </p>
 */
@SuperBuilder
@Getter
public abstract class ContentResponse<T> extends SuccessfulResponse {
    @JsonInclude(Include.NON_NULL)
    private T content;
}
