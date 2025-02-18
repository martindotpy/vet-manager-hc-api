package com.vluepixel.vetmanager.api.shared.adapter.out.persistence.repository.mysql;

import com.vluepixel.vetmanager.api.shared.domain.repository.RepositoryErrorType;

/**
 * MySQL error codes.
 */
public final class MySQLErrorCodes {
    /**
     * Get the repository failure type from the error code.
     *
     * @param errorCode the error code.
     * @return the failure type
     */
    public static RepositoryErrorType getTypeFromErrorCode(int errorCode) {
        return switch (errorCode) {
            case 1062 -> RepositoryErrorType.DUPLICATED;
            case 1048 -> RepositoryErrorType.NOT_NULL;
            case 1451 -> RepositoryErrorType.DELETE_ENTITY_REFERENCED;
            case 1452 -> RepositoryErrorType.FOREIGN_KEY_CONSTRAINT_FAIL;
            default -> RepositoryErrorType.UNEXPECTED;
        };
    }
}
