package com.vet.hc.api.auth.core.application.port.out;

import com.vet.hc.api.user.core.domain.model.User;

/**
 * Port for JWT authentication.
 */
public interface JwtAuthenticationPort {
    /**
     * Generate a JWT from a user info.
     *
     * @param user The user info.
     * @return The JWT
     */
    String toJwt(User user);

    /**
     * Check if a JWT is valid.
     *
     * @param jwt The JWT.
     * @return True if the JWT is valid, false otherwise
     */
    boolean isValid(String jwt);

    /**
     * Get the user DTO from a JWT.
     *
     * @param jwt The JWT.
     * @return The user DTO
     */
    User fromJwt(String jwt);
}
