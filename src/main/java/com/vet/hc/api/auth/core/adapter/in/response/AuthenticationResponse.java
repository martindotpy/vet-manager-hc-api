package com.vet.hc.api.auth.core.adapter.in.response;

import com.vet.hc.api.auth.core.domain.dto.JwtDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Response object for authentication.
 */
@Getter
@SuperBuilder
public class AuthenticationResponse extends ContentResponse<JwtDto> {
}
