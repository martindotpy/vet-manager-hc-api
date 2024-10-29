package com.vet.hc.api.shared.domain.repository;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumerates the possible failures that can occur in a repository.
 */
@Getter
@AllArgsConstructor
public enum RepositoryFailure implements Failure {
    DUPLICATE("Duplicate entry"),
    NOT_NULL("Null value not allowed"),
    UNEXPECTED("Unexpected error");

    private String message;
}
