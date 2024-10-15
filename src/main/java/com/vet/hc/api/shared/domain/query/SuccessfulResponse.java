package com.vet.hc.api.shared.domain.query;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Abstract class for a successful response.
 *
 * <p>
 * This is the base class for all successful responses. It is used to indicate
 * that the operation was successful.
 * </p>
 */
@Getter
@SuperBuilder
public abstract class SuccessfulResponse extends Response {
}
