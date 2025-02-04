package com.vet.hc.api.auth.core.adapter.in.response;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Authentication response. Contains JWT token.
 */
@SuperBuilder
public final class AuthenticationResponse extends ContentResponse<JwtDto> {
}
