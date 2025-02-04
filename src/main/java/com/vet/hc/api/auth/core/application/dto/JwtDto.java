package com.vet.hc.api.auth.core.application.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * JWT dto.
 */
@Getter
@Builder
public final class JwtDto {
    private String jwt;
}
