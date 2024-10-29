package com.vet.hc.api.client.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.in.request.UpdateFullDataClientRequest;
import com.vet.hc.api.client.adapter.out.mapper.ClientEmailMapper;
import com.vet.hc.api.client.adapter.out.mapper.ClientMapper;
import com.vet.hc.api.client.adapter.out.mapper.ClientPhoneMapper;
import com.vet.hc.api.client.domain.command.UpdateFullDataClientCommand;

/**
 * Mapper for updating clients.
 */
@Mapper(uses = { ClientMapper.class, ClientEmailMapper.class,
        ClientPhoneMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
