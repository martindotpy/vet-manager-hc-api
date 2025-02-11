package com.vet.hc.api.shared.adapter.out.config.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;

/**
 * Security auditor aware.
 *
 * <p>
 * The {@link ManyToOne} annotation must be added to the field with
 * {@link User} class to ensure proper functionality. In MySQL,
 * Hibernate maps the {@link UUID} field as <code>VARBINARY(255)</code>, while
 * the {@link User} ID field type is <code>UUID</code>.
 * <p/>
 */
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<User> {
    private final GetCurrentUserPort getCurrentUserPort;

    @Override
    public @NonNull Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(getCurrentUserPort.get());
    }
}
