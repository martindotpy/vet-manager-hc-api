package com.vluepixel.vetmanager.api.client.core.application.port.in;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.client.core.domain.request.UpdateClientRequest;

/**
 * Update client port.
 */
public interface UpdateClientPort {
    /**
     * Update client.
     *
     * @param request the update client request.
     * @return the updated client
     */
    ClientDto update(UpdateClientRequest request);
}
