package com.vet.hc.api.auth.adapter.in.response;

import com.vet.hc.api.shared.domain.query.Response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AuthenticationResponse extends Response<JwtDto> {
}
