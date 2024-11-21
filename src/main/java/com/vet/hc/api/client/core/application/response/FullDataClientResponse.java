package com.vet.hc.api.client.core.application.response;

import com.vet.hc.api.client.core.domain.dto.FullDataClientDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response to get the full data of a client.
 */
@SuperBuilder
public class FullDataClientResponse extends ContentResponse<FullDataClientDto> {
}
