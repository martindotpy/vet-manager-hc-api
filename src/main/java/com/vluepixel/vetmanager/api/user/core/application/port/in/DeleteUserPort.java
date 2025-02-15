package com.vluepixel.vetmanager.api.user.core.application.port.in;

/**
 * Delete user port.
 */
public interface DeleteUserPort {
    /**
     * Delete a user by id.
     *
     * @param id the id.
     */
    void deleteById(Long id);
}
