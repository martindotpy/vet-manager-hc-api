package com.vluepixel.vetmanager.api.client.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.client.core.domain.model.Client;
import com.vluepixel.vetmanager.api.client.core.domain.request.CreateClientRequest;
import com.vluepixel.vetmanager.api.client.core.domain.request.UpdateClientRequest;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Client mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface ClientMapper
        extends CrudMapper<Client, ClientDto, Client.ClientBuilder> {
    @ObjectFactory
    default Client.ClientBuilder createClientBuilder() {
        return Client.builder();
    }

    /**
     * Create client from request.
     *
     * @param request the create client request.
     * @return the client builder
     */
    @Mapping(target = "id", ignore = true)
    Client.ClientBuilder fromRequest(CreateClientRequest request);

    /**
     * Update client from request.
     *
     * @param request the update client request.
     * @return the client builder
     */
    Client.ClientBuilder fromRequest(UpdateClientRequest request);
}
