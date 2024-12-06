package com.vet.hc.api.shared.adapter.out.mapper;

import org.mapstruct.Mapper;

import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Mapper for repository failures.
 */
@Mapper(componentModel = "spring")
public interface RepositoryFailureMapper {
    /**
     * Maps a {@link MySQLRepositoryFailure} to a {@link RepositoryFailure}.
     *
     * @param repositoryFailure The repository failure to map.
     * @return The mapped repository failure
     */
    RepositoryFailure toRespositoryFailure(MySQLRepositoryFailure repositoryFailure);
}
