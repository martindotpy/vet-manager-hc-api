package com.vet.hc.api.client.adapter.in.mapper;

import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.in.request.UpdateClientEmailsRequest;
import com.vet.hc.api.client.domain.command.UpdateClientEmailsCommand;

/**
 * Mapper for updating client emails.
 */
public interface UpdateClientEmailsMapper {
    UpdateClientEmailsMapper INSTANCE = Mappers.getMapper(UpdateClientEmailsMapper.class);

    /**
     * Maps a request to a command.
     *
     * @param request The request.
     * @return The command
     */
    UpdateClientEmailsCommand toCommand(UpdateClientEmailsRequest request);
}
