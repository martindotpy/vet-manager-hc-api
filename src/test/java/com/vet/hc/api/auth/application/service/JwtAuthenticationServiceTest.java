package com.vet.hc.api.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vet.hc.api.shared.adapter.out.config.ApplicationProperties;
import com.vet.hc.api.user.application.response.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtAuthenticationServiceTest {
    @Mock
    private ApplicationProperties applicationProperties;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JwtAuthenticationService jwtAuthenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(applicationProperties.getSecurityJwtPassword())
                .thenReturn(Base64.getEncoder().encodeToString("a-very-secure-and-long-secret-key-12345".getBytes()));
        when(applicationProperties.getSecurityJwtExpiration()).thenReturn(3600000L);
    }

    @Test
    public void testGenerateJwt() {
        UserDto user = UserDto.builder()
                .id(1L)
                .email("john.doe@example.com")
                .build();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("email", user.getEmail());

        when(objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {
        })).thenReturn(userMap);

        String jwt = jwtAuthenticationService.generateJwt(user);

        assertNotNull(jwt);
    }

    @Test
    public void testIsValid() {
        UserDto user = UserDto.builder()
                .id(1L)
                .email("john.doe@example.com")
                .build();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("email", user.getEmail());

        when(objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {
        })).thenReturn(userMap);

        String jwt = jwtAuthenticationService.generateJwt(user);

        assertTrue(jwtAuthenticationService.isValid(jwt));
    }

    @Test
    public void testIsInvalid() {
        String invalidJwt = "invalid.jwt.token";

        assertFalse(jwtAuthenticationService.isValid(invalidJwt));
    }

    @Test
    public void testGetClaim() {
        UserDto user = UserDto.builder()
                .id(1L)
                .email("john.doe@example.com")
                .build();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("email", user.getEmail());

        when(objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {
        })).thenReturn(userMap);

        String jwt = jwtAuthenticationService.generateJwt(user);

        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(applicationProperties.getSecurityJwtPassword())))
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        assertNotNull(claims);
        assertEquals(String.valueOf(user.getId()), claims.getSubject());
    }
}