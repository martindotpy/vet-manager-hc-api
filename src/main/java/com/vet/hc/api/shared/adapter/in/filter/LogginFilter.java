package com.vet.hc.api.shared.adapter.in.filter;

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
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String realIp = req.getHeader("X-Real-IP");
        String path = req.getRequestURI();
        String method = req.getMethod();

        if (realIp != null) {
            log.info(String.format(
                    "\u001B[35m%s\u001B[0m to \u001B[35m%s\u001B[0m from \u001B[35m%s\u001B[0m IP",
                    method,
                    path,
                    realIp));
        } else {
            log.info(String.format(
                    "\u001B[35m%s\u001B[0m to \u001B[35m%s\u001B[0m",
                    method,
                    path));
        }

        chain.doFilter(req, res);
    }
}
