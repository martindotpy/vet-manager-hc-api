package com.vet.hc.api.shared.domain.repository;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Enumerates the possible failures that can occur in a repository.
 */
@Getter
@RequiredArgsConstructor
public enum RepositoryFailure implements Failure {
    DUPLICATED("Duplicate entry"),
    NOT_FOUND("Entry not found"),
    NOT_NULL("Null value not allowed"),
    UNEXPECTED("Unexpected error"),
    FIELD_NOT_FOUND("Field not found"),;

    private final String message;
    @Setter
    private String field;
}
