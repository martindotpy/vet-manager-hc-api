package com.vluepixel.vetmanager.api.shared.domain.repository;

/**
 * Enumerates the possible errors that can occur in a repository.
 */
public enum RepositoryErrorType {
    DUPLICATED,
    VALIDATION_FAIL,
    NOT_FOUND,
    NOT_NULL,
    NON_UNIQUE_RESULT,
    FIELD_NOT_FOUND,
    OPTIMISTIC_LOCK_FAIL,
    FOREIGN_KEY_CONSTRAINT_FAIL,
    UNEXPECTED;
}
