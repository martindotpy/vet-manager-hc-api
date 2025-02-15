package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;

/**
 * Find user port.
 */
public interface FindUserPort {
    /**
     * Find all users by paginated criteria.
     *
     * @param criteria the paginated criteria.
     * @return users found paginated
     */
    Paginated<UserDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find user by id.
     *
     * @param id the id.
     * @return user found
     */
    UserDto findById(Long id);
}
