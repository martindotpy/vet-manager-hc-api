package com.vet.hc.api.auth.core.adapter.out.bean;

import org.mindrot.jbcrypt.BCrypt;

import lombok.NoArgsConstructor;

/**
 * Password encoder.
 */
@NoArgsConstructor
public class PasswordEncoder {
    /**
     * Encodes a password.
     *
     * @param password The password to encode.
     * @return The encoded password
     */
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Matches a password with a hashed password.
     *
     * @param password       The password.
     * @param hashedPassword The hashed password.
     * @return True if the password matches the hashed password, false otherwise.
     */
    public boolean matches(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
