package com.vet.hc.api.auth.adapter.in.response;

import com.vet.hc.api.shared.domain.query.ContentResponse;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Response object for authentication.
 */
@Getter
@SuperBuilder
public class AuthenticationResponse extends ContentResponse<JwtDto> {
}
