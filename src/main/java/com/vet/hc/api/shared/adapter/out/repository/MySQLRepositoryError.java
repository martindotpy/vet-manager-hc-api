package com.vet.hc.api.shared.adapter.out.repository;

import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.shared.domain.repository.RepositoryFailureType;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class MySQLRepositoryError extends RepositoryFailure {
    /**
     * Maps an error code to a {@link MySQLRepositoryFailureType}.
     *
     * @param errorCode The error code to map.
     * @return The mapped repository error
     */
    public static MySQLRepositoryError from(int errorCode, String mySqlMessage) {
        RepositoryFailureType error = switch (errorCode) {
            case 1062 -> RepositoryFailureType.DUPLICATED;
            case 1048 -> RepositoryFailureType.NOT_NULL;
            default -> RepositoryFailureType.UNEXPECTED;
        };

        MySQLRepositoryError failure = MySQLRepositoryError.builder()
                .type(error)
                .message(mySqlMessage)
                .build();

        return failure;
    }
}
