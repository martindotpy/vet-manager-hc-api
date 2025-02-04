package com.vet.hc.api.image.core.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vet.hc.api.image.core.adapter.out.persistence.entity.ImageEntity;

/**
 * Image spring repository.
 */
public interface ImageSpringRepository extends JpaRepository<ImageEntity, Long> {
}
