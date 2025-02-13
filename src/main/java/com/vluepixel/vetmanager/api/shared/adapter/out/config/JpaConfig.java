package com.vluepixel.vetmanager.api.shared.adapter.out.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.shared.adapter.out.config.impl.SecurityAuditorAware;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

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
     * @return the auditor aware
     */
    @Bean
    AuditorAware<User> auditorProvider(
            GetCurrentUserPort getCurrentUserPort) {
        return new SecurityAuditorAware(getCurrentUserPort);
    }
}
