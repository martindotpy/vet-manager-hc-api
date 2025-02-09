package com.vet.hc.api.user.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import org.slf4j.MDC;

import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.criteria.PaginatedCriteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.application.port.in.FindUserPort;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
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
    public Result<UserDto, UserFailure> findById(Long id) {
        MDC.put("operationId", "User id " + id);
        log.info("Finding user by id {}",
                fgBrightBlue(id));

        var user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.error("User not found by id {}",
                    fgBrightBlue(id));

            return failure(UserFailure.NOT_FOUND);
        }

        log.info("User found by id {}",
                fgBrightGreen(id));

        return ok(userMapper.toDto(user.get()));
    }

    @Override
    public Result<Paginated<UserDto>, UserFailure> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Find users");
        log.info("Finding users by criteria {}",
                fgBrightBlue(criteria));

        var result = userRepository.findPaginatedBy(criteria);

        if (result.isFailure()) {
            log.error("Users not found by criteria: {}",
                    fgBrightBlue(result.getFailure()));

            return failure(result);
        }

        var usersPaginated = result.getOk();

        log.info("Users {} found by criteria",
                fgBrightGreen(usersPaginated.getSize()));

        return ok(usersPaginated.map(userMapper::toDto));
    }
}
