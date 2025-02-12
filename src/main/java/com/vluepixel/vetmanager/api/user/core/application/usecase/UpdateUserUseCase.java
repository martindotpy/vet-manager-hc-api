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
import com.vluepixel.vetmanager.api.user.core.domain.payload.UpdateUserPayload;
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
    public UserDto update(UpdateUserPayload payload) {
        var result = updateHelper(payload);

        return userMapper.toDto(result);
    }

    @Override
    @Transactional
    public JwtDto updateCurrentUser(UpdateUserPayload payload) {
        var result = updateHelper(payload);

        String jwt = jwtAuthenticationPort.toJwt(result);

        return new JwtDto(jwt);
    }

    /**
     * Updates the current user.
     *
     * @param payload the payload.
     * @return the result
     */
    private User updateHelper(UpdateUserPayload payload) {
        User user = getCurrentUserPort.get();
        MDC.put("operationId", "User id " + user.getId());
        log.info("Updating user with id {}",
                fgBrightBlue(payload.getId()));

        var result = userRepository.update(payload.getId(),
                FieldUpdate.set("firstName", payload.getFirstName().trim()),
                FieldUpdate.set("lastName", payload.getLastName().trim()));

        return result;
    }
}
