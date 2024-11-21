package com.vet.hc.api.auth.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Response for authentication, including the JWT.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class JwtDto {
    private String jwt;
}
