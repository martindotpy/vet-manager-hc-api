package com.vet.hc.api.auth.adapter.in.response;

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
public class JwtDto {
    private String jwt;
}
