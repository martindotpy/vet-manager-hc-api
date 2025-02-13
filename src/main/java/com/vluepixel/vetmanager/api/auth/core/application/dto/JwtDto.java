package com.vluepixel.vetmanager.api.auth.core.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JWT dto.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class JwtDto {
    private String jwt;
}
