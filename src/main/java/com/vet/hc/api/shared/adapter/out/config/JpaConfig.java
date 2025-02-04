package com.vet.hc.api.shared.adapter.out.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.shared.adapter.out.config.impl.SecurityAuditorAware;
import com.vet.hc.api.user.core.adapter.out.persistence.entity.UserEntity;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Jpa auditory configuration.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
    /**
     * Auditor provider.
     *
     * @param getCurrentUserPort the get current user port.
     * @param userMapper         the user mapper.
     * @return the auditor aware
     */
    @Bean
    AuditorAware<UserEntity> auditorProvider(
            GetCurrentUserPort getCurrentUserPort,
            UserMapper userMapper) {
        return new SecurityAuditorAware(getCurrentUserPort, userMapper);
    }
}
