package com.vet.hc.api.auth.application.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vet.hc.api.auth.application.port.in.JwtAuthenticationPort;
import com.vet.hc.api.shared.adapter.out.bean.ApplicationConfig;
import com.vet.hc.api.user.application.response.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for JWT authentication.
 */
@NoArgsConstructor
public class JwtAuthenticationService implements JwtAuthenticationPort {
    private ApplicationConfig applicationConfig;
    private ObjectMapper objectMapper;
    private Key key;

    @Inject
    public JwtAuthenticationService(ApplicationConfig applicationConfig, ObjectMapper objectMapper) {
        this.applicationConfig = applicationConfig;
        this.objectMapper = objectMapper;

        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(applicationConfig.getSecurityJwtPassword()));
    }

    /**
     * Generate a JWT for a user.
     *
     * @param user The user to generate the JWT for.
     * @return The generated JWT
     */
    @Override
    public String generateJwt(UserDto user) {
        Long expirationTime = applicationConfig.getSecurityJwtExpiration();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        String jwt = Jwts.builder()
                .claims(
                        new HashMap<String, Object>() {
                            {
                                put("user", objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {
                                }));
                            }
                        })
                .subject(String.valueOf(user.getId()))
                .expiration(expiration)
                .signWith(key)
                .issuedAt(now)
                .expiration(expiration)
                .compact();

        return jwt;
    }

    /**
     * Validate a JWT.
     *
     * @param jwt The JWT to validate.
     * @return True if the JWT is valid, false otherwise
     */
    @Override
    public boolean isValid(String jwt) {
        try {
            Claims claims = getClaims(jwt);

            return !isExpired(claims);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the user from a JWT.
     *
     * @param jwt The JWT to get the user from.
     * @return The user from the JWT, returns null if the JWT is invalid
     */
    @Override
    public UserDto getUser(String jwt) {
        try {
            return getClaim(jwt, claims -> objectMapper.convertValue(claims.get("user"), UserDto.class));
        } catch (Exception e) {
            return null;
        }
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

    /**
     * Get the claims from a JWT.
     *
     * @param jwt The JWT to get the claims from.
     * @return The claims from the JWT.
     */
    private Claims getClaims(String jwt) {
        SecretKey secretKey = new SecretKeySpec(key.getEncoded(), key.getAlgorithm());

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    /**
     * Check if a JWT is expired.
     *
     * @param claims The claims to check.
     * @return True if the JWT is expired, false otherwise
     */
    private boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
