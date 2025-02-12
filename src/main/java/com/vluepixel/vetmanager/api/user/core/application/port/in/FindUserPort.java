package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;

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
    UserDto findById(Long id);

    /**
     * Find all users.
     *
     * @return the users.
     */
    Paginated<UserDto> findPaginatedBy(PaginatedCriteria criteria);
}
