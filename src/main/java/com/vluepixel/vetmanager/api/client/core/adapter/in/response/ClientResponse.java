package com.vluepixel.vetmanager.api.client.core.adapter.in.response;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Client response.
 */
@SuperBuilder
public final class ClientResponse extends ContentResponse<ClientDto> {
}
