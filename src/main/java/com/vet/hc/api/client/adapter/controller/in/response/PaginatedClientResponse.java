package com.vet.hc.api.client.adapter.controller.in.response;

import com.vet.hc.api.shared.domain.query.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response to get a paginated list of clients.
 */
@SuperBuilder
public class PaginatedClientResponse extends PaginatedResponse<ClientDto> {
}
