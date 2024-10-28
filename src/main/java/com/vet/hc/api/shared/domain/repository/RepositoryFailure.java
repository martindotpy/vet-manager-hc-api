package com.vet.hc.api.shared.domain.repository;

import com.vet.hc.api.shared.domain.query.Failure;

/**
 * Enumerates the possible failures that can occur in a repository.
 */
public enum RepositoryFailure implements Failure {
    DUPLICATE,
    UNEXPECTED
}
