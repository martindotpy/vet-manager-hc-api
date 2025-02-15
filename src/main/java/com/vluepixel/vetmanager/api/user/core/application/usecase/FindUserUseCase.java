package com.vluepixel.vetmanager.api.user.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.application.mapper.UserMapper;
import com.vluepixel.vetmanager.api.user.core.application.port.in.FindUserPort;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Find user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindUserUseCase implements FindUserPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Paginated<UserDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Users by criteria: " + fgBrightBlue(criteria.hashCode()));
        log.info("Finding users by {}",
                fgBrightBlue(criteria));

        Paginated<User> paginatedUseres = userRepository.findPaginatedBy(criteria);

        log.info("{} users found",
                fgBrightGreen(paginatedUseres.getContent().size()));

        return paginatedUseres.map(userMapper::toDto);
    }

    @Override
    public UserDto findById(Long id) {
        MDC.put("operationId", "User id " + id);
        log.info("Finding user");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));

        log.info("User found");

        return userMapper.toDto(user);
    }
}
