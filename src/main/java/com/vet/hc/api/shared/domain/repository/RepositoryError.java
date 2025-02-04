package com.vet.hc.api.shared.domain.repository;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a error in the repository layer.
 */
@Getter
@Builder
public class RepositoryError {
    private RepositoryErrorType type;
    private String field;
}
