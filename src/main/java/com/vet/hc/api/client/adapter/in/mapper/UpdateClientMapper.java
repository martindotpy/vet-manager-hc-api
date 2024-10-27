package com.vet.hc.api.client.adapter.in.mapper;

import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.in.request.UpdateClientRequest;
import com.vet.hc.api.client.domain.command.UpdateClientCommand;

/**
 * Mapper for updating clients.
 */
public interface UpdateClientMapper {
    UpdateClientMapper INSTANCE = Mappers.getMapper(UpdateClientMapper.class);

    /**
     * Maps a request to a command.
     *
     * @param request The request.
     * @return The command
     */
    UpdateClientCommand toCommand(UpdateClientRequest request);
}
