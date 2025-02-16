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
    /**
     * Creates a new {@link Client} builder.
     *
     * @return the builder
     */
    @ObjectFactory
    default Client.ClientBuilder createClientBuilder() {
        return Client.builder();
    }

    /**
     * Create client from request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>id</code></li>
     * <li><code>deleted</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the create client request.
     * @return the client builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Client.ClientBuilder fromRequest(CreateClientRequest request);

    /**
     * Update client from request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>deleted</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the update client request.
     * @return the client builder
     */
    @Mapping(target = "deleted", ignore = true)
    Client.ClientBuilder fromRequest(UpdateClientRequest request);
}
