package com.vluepixel.vetmanager.api.client.core.application.port.in;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.client.core.domain.request.CreateClientRequest;

/**
 * Create client port.
 */
public interface CreateClientPort {
    /**
     * Create client .
     *
     * @param request the create client request.
     * @return the created client
     */
    ClientDto create(CreateClientRequest request);
}
