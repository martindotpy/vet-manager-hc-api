package com.vet.hc.api.shared.adapter.in.filter;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Filter to log requests.
 */
@Slf4j
@NoArgsConstructor
public final class LogginFilter extends HttpFilter {
    private final String[] ignoredPaths = {
            "/favicon.ico",
            "/api/v0/swagger-ui/.*\\.(css|js|png)"
    };

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String realIp = req.getHeader("X-Real-IP");
        String path = req.getRequestURI();
        String method = req.getMethod();

        if (path.matches(String.join("|", ignoredPaths))) {
            chain.doFilter(req, res);
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(
                String.format(
                        "%s to %s",
                        ansi().fgMagenta().a(method).reset(),
                        ansi().fgMagenta().a(path).reset()));

        if (realIp != null)
            sb.append(String.format(" from %s", ansi().fgCyan().a(realIp).reset()));

        log.info(sb.toString());

        chain.doFilter(req, res);
    }
}
