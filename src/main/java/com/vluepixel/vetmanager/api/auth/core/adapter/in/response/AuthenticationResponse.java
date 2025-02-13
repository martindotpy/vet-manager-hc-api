package com.vluepixel.vetmanager.api.auth.core.adapter.in.response;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Authentication response. Contains JWT token.
 */
@SuperBuilder
public final class AuthenticationResponse extends ContentResponse<JwtDto> {
}
