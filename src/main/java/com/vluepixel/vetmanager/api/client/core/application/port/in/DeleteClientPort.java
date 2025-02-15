package com.vluepixel.vetmanager.api.client.core.application.port.in;

/**
 * Delete client port.
 */
public interface DeleteClientPort {
    /**
     * Delete client by id.
     *
     * @param id the client id.
     */
    void deleteById(Long id);
}
