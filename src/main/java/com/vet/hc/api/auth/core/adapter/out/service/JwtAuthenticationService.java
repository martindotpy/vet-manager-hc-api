package com.vet.hc.api.auth.core.adapter.out.service;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for JWT authentication.
 *
 * <p>
 * The token has no expiration date.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public final class JwtAuthenticationService implements JwtAuthenticationPort {
    @Value("${security.jwt.secret}")
    private String secretKey;
    private Key key;

    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @Override
    public String toJwt(User user) {
        Objects.requireNonNull(user, "User must not be null");
        UserDto userDto = userMapper.toDto(user);

        String jwt = Jwts.builder()
                .claims(
                        new HashMap<String, Object>() {
                            {
                                put("user",
                                        objectMapper.convertValue(userDto,
                                                new TypeReference<Map<String, Object>>() {
                                                }));
                            }
                        })
                .subject(String.valueOf(user.getId()))
                .signWith(getKey())
                .compact();

        return jwt;
    }

    @Override
    public boolean isValid(String jwt) {
        try {
            getClaims(jwt);
            return true;
        } catch (Exception e) {
            log.error("Error while validating JWT", e);

            return false;
        }
    }

    @Override
    public User fromJwt(String jwt) {
        try {
            return getClaim(
                    jwt, claims -> userMapper.toDomain(objectMapper.convertValue(claims.get("user"), UserDto.class)));
        } catch (Exception e) {
            log.error("Error while getting user from JWT", e);

            return null;
        }
    }

    /**
     * Get the claims from a JWT.
     *
     * @param jwt The JWT to get the claims from.
     * @return The claims from the JWT
     * @throws Exception If the JWT is invalid.
     */
    private Claims getClaims(String jwt) {
        SecretKey secretKey = new SecretKeySpec(getKey().getEncoded(), getKey().getAlgorithm());

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    /**
     * Get a claim from a JWT.
     *
     * @param <T>      The type of the claim.
     * @param jwt      The JWT to get the claim from.
     * @param resolver The resolver to get the claim.
     * @return The claim from the JWT
     */
    public <T> T getClaim(String jwt, Function<Claims, T> resolver) {
        Claims claims = getClaims(jwt);

        return resolver.apply(claims);
    }

    private Key getKey() {
        if (key == null) {
            key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        }

        return key;
    }
}
