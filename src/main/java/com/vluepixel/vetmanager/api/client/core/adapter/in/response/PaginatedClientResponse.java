package com.vluepixel.vetmanager.api.client.core.adapter.in.response;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated client response.
 */
@SuperBuilder
public final class PaginatedClientResponse extends PaginatedResponse<ClientDto> {
}
