package com.vet.hc.api.shared.adapter.out.repository;

/**
 * Enumerates the possible failures that can occur in a MySQL repository.
 */
public enum MySQLRepositoryFailure {
    DUPLICATE,
    NOT_NULL,
    UNEXPECTED;

    /**
     * Maps an error code to a {@link MySQLRepositoryFailure}.
     *
     * @param errorCode The error code to map.
     * @return The mapped repository failure
     */
    public static MySQLRepositoryFailure from(int errorCode) {
        return switch (errorCode) {
            case 1062 -> DUPLICATE;
            case 1048 -> NOT_NULL;
            default -> UNEXPECTED;
        };
    }
}
