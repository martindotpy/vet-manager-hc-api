package com.vet.hc.api.shared.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Mapper for repository failures.
 */
@Mapper
public interface RepositoryFailureMapper {
    RepositoryFailureMapper INSTANCE = Mappers.getMapper(RepositoryFailureMapper.class);

    /**
     * Maps a {@link MySQLRepositoryFailure} to a {@link RepositoryFailure}.
     *
     * @param repositoryFailure The repository failure to map.
     * @return The mapped repository failure
     */
    RepositoryFailure toRespositoryFailure(MySQLRepositoryFailure repositoryFailure);
}
