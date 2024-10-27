package com.vet.hc.api.client.adapter.in.mapper;

import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.in.request.CreateClientRequest;
import com.vet.hc.api.client.domain.command.CreateClientCommand;

/**
 * Mapper for creating a client.
 */
public interface CreateClientMapper {
    CreateClientMapper INSTANCE = Mappers.getMapper(CreateClientMapper.class);

    /**
     * Maps a request to a command.
     *
     * @param request The request.
     * @return The command
     */
    CreateClientCommand toCommand(CreateClientRequest request);
}
