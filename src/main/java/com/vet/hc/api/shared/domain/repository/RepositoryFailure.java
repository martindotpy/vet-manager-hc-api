package com.vet.hc.api.shared.domain.repository;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a failure in the repository layer.
 */
@Getter
@SuperBuilder
public class RepositoryFailure implements Failure {
    private RepositoryFailureType type;
    private String message;
    private String field;
}
