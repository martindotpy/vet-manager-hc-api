package com.vet.hc.api.shared.domain.repository;

/**
 * Enumerates the possible errors that can occur in a repository.
 */
public enum RepositoryFailureType {
    DUPLICATED,
    NOT_FOUND,
    NOT_NULL,
    UNEXPECTED,
    INNER_ENTITY_NOT_FOUND,
    FIELD_NOT_FOUND;
}
