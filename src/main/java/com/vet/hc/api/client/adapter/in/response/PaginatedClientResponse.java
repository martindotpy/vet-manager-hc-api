package com.vet.hc.api.client.adapter.in.response;

import java.util.List;

import com.vet.hc.api.client.application.dto.ClientDto;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response to get a paginated list of clients.
 */
@SuperBuilder
public class PaginatedClientResponse extends PaginatedResponse<List<ClientDto>> {
}
