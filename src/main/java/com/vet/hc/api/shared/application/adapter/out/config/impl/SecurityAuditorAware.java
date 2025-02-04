package com.vet.hc.api.shared.application.adapter.out.config.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.user.core.adapter.out.persistence.entity.UserEntity;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;

/**
 * Security auditor aware.
 *
 * <p>
 * The {@link ManyToOne} annotation must be added to the field with
 * {@link UserEntity} class to ensure proper functionality. In MySQL,
 * Hibernate maps the {@link UUID} field as <code>VARBINARY(255)</code>, while
 * the {@link UserEntity} ID field type is <code>UUID</code>.
 * <p/>
 */
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<UserEntity> {
    private final GetCurrentUserPort getCurrentUserPort;
    private final UserMapper userMapper;

    @Override
    public @NonNull Optional<UserEntity> getCurrentAuditor() {
        return Optional.ofNullable(userMapper.toEntity(getCurrentUserPort.get()));
    }
}
