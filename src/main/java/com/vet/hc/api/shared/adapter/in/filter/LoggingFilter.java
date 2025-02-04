package com.vet.hc.api.shared.adapter.in.filter;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightMagenta;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.lang.NonNull;
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
public final class LoggingFilter extends OncePerRequestFilter {
    private final String[] ignoredPaths = {
            "/favicon.ico",
            "/api/.*/swagger-ui/.*\\.(css|js|png)"
    };

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String method = request.getMethod();
            String realIp = request.getHeader("X-Real-IP");
            String path = request.getRequestURI();

            if (path.matches(String.join("|", ignoredPaths))) {
                filterChain.doFilter(request, response);
                return;
            }

            StringBuilder message = new StringBuilder();

            message.append(
                    String.format(
                            "%s to %s",
                            fgBrightMagenta(method),
                            fgBrightMagenta(path)));

            if (realIp != null) {
                MDC.put("realIp", realIp);
            }

            log.info(message.toString());

            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}