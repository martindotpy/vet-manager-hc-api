package com.vet.hc.api.shared.adapter.in.filter;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Filter to log requests.
 */
@Slf4j
@Component
public final class LogginFilter extends OncePerRequestFilter {
    private final String[] ignoredPaths = {
            "/favicon.ico",
            "/api/v0/swagger-ui/.*\\.(css|js|png)"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String method = request.getMethod();
        String realIp = request.getHeader("X-Real-IP");
        String path = request.getRequestURI();

        if (path.matches(String.join("|", ignoredPaths))) {
            filterChain.doFilter(request, response);
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(
                String.format(
                        "%s to %s",
                        ansi().fgMagenta().a(method).reset(),
                        ansi().fgMagenta().a(path).reset()));

        if (realIp != null) {
            sb.append(String.format(" from %s", ansi().fgCyan().a(realIp).reset()));
        }

        log.info(sb.toString());

        filterChain.doFilter(request, response);
    }
}