package com.vet.hc.api.client.adapter.controller.in.mapper;

import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.controller.in.request.UpdateClientPhonesRequest;
import com.vet.hc.api.client.domain.command.UpdateClientPhonesCommand;

/**
 * Mapper for updating client phones.
 */
public interface UpdateClientPhonesMapper {
    UpdateClientPhonesMapper INSTANCE = Mappers.getMapper(UpdateClientPhonesMapper.class);

    /**
     * Maps a request to a command.
     *
     * @param request The request.
     * @return The command
     */
    UpdateClientPhonesCommand toCommand(UpdateClientPhonesRequest request);
}
