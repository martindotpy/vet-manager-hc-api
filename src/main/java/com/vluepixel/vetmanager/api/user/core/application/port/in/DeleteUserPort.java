package com.vluepixel.vetmanager.api.user.core.application.port.in;

/**
 * Delete user port.
 */
public interface DeleteUserPort {
    /**
     * Delete a user.
     *
     * @param id the id of the user to delete.
     * @return the result of the operation.
     */
    void deleteById(Long id);
}
