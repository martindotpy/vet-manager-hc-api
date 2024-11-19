package com.vet.hc.api.auth.adapter.in.filter;

import java.io.IOException;

import com.vet.hc.api.shared.adapter.out.config.ApplicationProperties;

import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Filter to handle CORS.
 */
@Slf4j
@NoArgsConstructor
public class CorsFilter extends HttpFilter {
    private ApplicationProperties applicationProperties;

    @Inject
    public CorsFilter(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;

        log.info("cors filter created successfully");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        res.setHeader("Access-Control-Allow-Origin", String.join(",", applicationProperties.getSecurityCorsAllowedOrigins()));
        res.setHeader("Access-Control-Allow-Methods", String.join(",", applicationProperties.getSecurityCorsAllowedMethods()));
        res.setHeader("Access-Control-Allow-Headers", String.join(",", applicationProperties.getSecurityCorsAllowedHeaders()));

        chain.doFilter(req, res);
    }
}
