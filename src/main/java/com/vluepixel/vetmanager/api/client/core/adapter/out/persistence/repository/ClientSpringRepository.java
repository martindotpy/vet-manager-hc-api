package com.vluepixel.vetmanager.api.client.core.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.client.core.domain.model.Client;

/**
 * Spring Data JPA repository for {@link Client}.
 */
public interface ClientSpringRepository extends JpaRepository<Client, Long> {
}
