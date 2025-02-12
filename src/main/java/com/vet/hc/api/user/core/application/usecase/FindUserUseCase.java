package com.vet.hc.api.user.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.criteria.PaginatedCriteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.application.port.in.FindUserPort;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

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
    public UserDto findById(Long id) {
        MDC.put("operationId", "User id " + id);
        log.info("Finding user by id {}",
                fgBrightBlue(id));

        var user = userRepository.findById(id).orElseThrow();

        log.info("User found by id {}",
                fgBrightGreen(id));

        return userMapper.toDto(user);
    }

    @Override
    public Paginated<UserDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Find users");
        log.info("Finding users by criteria {}",
                fgBrightBlue(criteria));

        var result = userRepository.findPaginatedBy(criteria);

        log.info("Users {} found by criteria",
                fgBrightGreen(result.getSize()));

        return result.map(userMapper::toDto);
    }
}
