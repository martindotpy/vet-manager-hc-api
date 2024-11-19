package com.vet.hc.api.auth.adapter.in.filter;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import com.vet.hc.api.auth.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.shared.adapter.out.config.ApplicationProperties;
import com.vet.hc.api.user.domain.dto.UserDto;
import com.vet.hc.api.user.domain.enums.UserRole;

import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Filter to validate JWT tokens.
 */
@Slf4j
@NoArgsConstructor
public class JwtFilter extends HttpFilter {
    private ApplicationProperties applicationProperties;
    private JwtAuthenticationPort jwtAuthenticationPort;

    @Inject
    public JwtFilter(ApplicationProperties applicationProperties, JwtAuthenticationPort jwtAuthenticationPort) {
        this.applicationProperties = applicationProperties;
        this.jwtAuthenticationPort = jwtAuthenticationPort;

        log.info("jwt filter created successfully");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String path = req.getRequestURI();

        if (path.startsWith("/api")) {
            doFilterApiRoutes(req, res, chain);
            return;
        }

        chain.doFilter(req, res);
    }

    /**
     * Filter API routes.
     *
     * @param req   The request.
     * @param res   The response.
     * @param chain The filter chain.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet error occurs.
     */
    private void doFilterApiRoutes(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String[] routeParts = req.getRequestURI().split("/");

        if (routeParts.length < 3) {
            res.setStatus(403);
            return;
        }

        String[] restRouteParts = new String[routeParts.length - 3];
        System.arraycopy(routeParts, 3, restRouteParts, 0, restRouteParts.length);
        String restRoute = "/" + String.join("/", restRouteParts);

        for (String publicRoute : applicationProperties.getSecurityApiPublicEndpoints()) {
            if (FilenameUtils.wildcardMatch(restRoute, publicRoute)) {
                doFilterPublicRoutes(req, res, chain);
                return;
            }
        }

        doFilterPrivateRoutes(req, res, chain, restRoute);
    }

    /**
     * Filter public routes.
     *
     * <p>
     * Public routes are those that do not require authentication. Define the public
     * routes in <code>application.properties</code>.
     * </p>
     *
     * @param req   The request.
     * @param res   The response.
     * @param chain The filter chain.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet error occurs.
     */
    private void doFilterPublicRoutes(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(req, res);
    }

    /**
     * Filter private routes.
     *
     * @param req       The request.
     * @param res       The response.
     * @param chain     The filter chain.
     * @param restRoute The REST route.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet error occurs.
     */
    private void doFilterPrivateRoutes(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            String restRoute)
            throws IOException, ServletException {
        String authorization = req.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            res.setStatus(403);
            return;
        }

        String token = authorization.substring(7);

        if (!jwtAuthenticationPort.isValid(token)) {
            res.setStatus(403);
            return;
        }

        UserDto user = jwtAuthenticationPort.getUser(token);
        req.setAttribute("user", user);

        for (String adminRoute : applicationProperties.getSecurityApiAdminEndpoints()) {
            if (FilenameUtils.wildcardMatch(restRoute, adminRoute)) {
                if (!user.getRoles().contains(UserRole.ADMIN)) {
                    res.setStatus(403);
                    return;
                }
            }
        }

        for (String vetRoute : applicationProperties.getSecurityApiVetEndpoints()) {
            if (FilenameUtils.wildcardMatch(restRoute, vetRoute)) {
                if (!user.getRoles().contains(UserRole.VET)) {
                    res.setStatus(403);
                    return;
                }
            }
        }

        chain.doFilter(req, res);
    }
}
