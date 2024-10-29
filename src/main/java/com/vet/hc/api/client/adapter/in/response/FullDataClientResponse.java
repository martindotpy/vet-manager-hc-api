package com.vet.hc.api.client.adapter.in.response;

import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.shared.domain.query.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response to get the full data of a client.
 */
@SuperBuilder
public class FullDataClientResponse extends ContentResponse<FullDataClientDto> {
}
