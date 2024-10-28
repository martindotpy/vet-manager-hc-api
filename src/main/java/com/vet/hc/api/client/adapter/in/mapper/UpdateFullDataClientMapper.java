package com.vet.hc.api.client.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.in.request.UpdateFullDataClientRequest;
import com.vet.hc.api.client.domain.command.UpdateFullDataClientCommand;

/**
 * Mapper for updating clients.
 */
@Mapper
public interface UpdateFullDataClientMapper {
    UpdateFullDataClientMapper INSTANCE = Mappers.getMapper(UpdateFullDataClientMapper.class);

    /**
     * Maps a request to a command.
     *
     * @param request The request.
     * @return The command
     */
    UpdateFullDataClientCommand toCommand(UpdateFullDataClientRequest request);
}
