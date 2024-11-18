package com.vet.hc.api.patient.domain.response;

import com.vet.hc.api.client.domain.dto.ClientDto;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response to get a paginated list of clients.
 */
@SuperBuilder
public class PaginatedClientResponse extends PaginatedResponse<ClientDto> {
}
