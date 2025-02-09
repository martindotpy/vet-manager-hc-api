package com.vet.hc.api.user.core.application.port.in;

import com.vet.hc.api.shared.domain.criteria.PaginatedCriteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.failure.UserFailure;

/**
 * Find user port.
 */
public interface FindUserPort {
    /**
     * Find user by id.
     *
     * @param id the id.
     * @return the user.
     */
    Result<UserDto, UserFailure> findById(Long id);

    /**
     * Find all users.
     *
     * @return the users.
     */
    Result<Paginated<UserDto>, UserFailure> findPaginatedBy(PaginatedCriteria criteria);
}
