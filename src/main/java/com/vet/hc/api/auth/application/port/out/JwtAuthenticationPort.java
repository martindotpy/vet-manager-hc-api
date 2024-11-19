package com.vet.hc.api.auth.application.port.out;

import com.vet.hc.api.user.domain.dto.UserDto;

/**
 * Port for JWT authentication.
 */
public interface JwtAuthenticationPort {
    /**
     * Generate a JWT for a user.
     *
     * @param user The user to generate the JWT for.
     * @return The generated JWT
     */
    String generateJwt(UserDto user);

    /**
     * Validate a JWT.
     *
     * @param jwt The JWT to validate.
     * @return True if the JWT is valid, false otherwise
     */
    boolean isValid(String jwt);

    /**
     * Get the user from a JWT.
     *
     * @param jwt The JWT to get the user from.
     * @return The user from the JWT
     */
    UserDto getUser(String jwt);
}
