package com.vet.hc.api.auth.adapter.in.filter;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;

import com.vet.hc.api.auth.application.port.in.JwtAuthenticationPort;
import com.vet.hc.api.shared.adapter.out.config.ApplicationProperties;
import com.vet.hc.api.user.application.response.UserDto;

import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

/**
 * Filter to validate JWT tokens.
 */
@NoArgsConstructor
public class JwtFilter extends HttpFilter {
    private ApplicationProperties applicationProperties;
    private JwtAuthenticationPort jwtAuthenticationPort;

    private FileSystem fileSystem;

    @Inject
    public JwtFilter(ApplicationProperties applicationProperties, JwtAuthenticationPort jwtAuthenticationPort) {
        this.applicationProperties = applicationProperties;
        this.jwtAuthenticationPort = jwtAuthenticationPort;
        this.fileSystem = FileSystems.getDefault();
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
            PathMatcher publicRouteMatcher = fileSystem.getPathMatcher("glob:" + publicRoute);
            if (publicRouteMatcher.matches(fileSystem.getPath(restRoute))) {
                doFilterPublicRoutes(req, res, chain);
                return;
            }
        }

        doFilterPrivateRoutes(req, res, chain);
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
     * @param req   The request.
     * @param res   The response.
     * @param chain The filter chain.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet error occurs.
     */
    private void doFilterPrivateRoutes(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
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

        chain.doFilter(req, res);
    }
}
