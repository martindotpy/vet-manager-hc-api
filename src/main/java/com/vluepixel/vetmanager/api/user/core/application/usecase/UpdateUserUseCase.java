package com.vluepixel.vetmanager.api.user.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;

import org.jboss.logging.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.application.mapper.UserMapper;
import com.vluepixel.vetmanager.api.user.core.application.port.in.UpdateUserPort;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserRequest;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateUserUseCase implements UpdateUserPort {
    private final GetCurrentUserPort getCurrentUserPort;

    private final JwtAuthenticationPort jwtAuthenticationPort;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto update(UpdateUserRequest request) {
        var result = updateHelper(request);

        return userMapper.toDto(result);
    }

    @Override
    @Transactional
    public JwtDto updateCurrentUser(UpdateUserRequest request) {
        var result = updateHelper(request);

        String jwt = jwtAuthenticationPort.toJwt(result);

        return new JwtDto(jwt);
    }

    /**
     * Updates the current user.
     *
     * @param request the request.
     * @return the result
     */
    private User updateHelper(UpdateUserRequest request) {
        User user = getCurrentUserPort.get();
        MDC.put("operationId", "User id " + user.getId());
        log.info("Updating user with id {}",
                fgBrightBlue(request.getId()));

        var result = userRepository.update(request.getId(),
                FieldUpdate.set("firstName", request.getFirstName().trim()),
                FieldUpdate.set("lastName", request.getLastName().trim()));

        return result;
    }
}
