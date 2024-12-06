package com.vet.hc.api.auth.core.adapter.in.filter;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Filter to validate JWT tokens.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtAuthenticationPort jwtAuthenticationPort;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        User user = jwtAuthenticationPort.fromJwt(token);

        if (user == null) {
            log.warn("JWT token is invalid");

            filterChain.doFilter(request, response);
            return;
        }

        log.debug("JWT token is valid");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        log.info("User {} successfully authenticated", ansi().fgBrightGreen().a(user.getEmail()).reset());

        filterChain.doFilter(request, response);
    }
}
