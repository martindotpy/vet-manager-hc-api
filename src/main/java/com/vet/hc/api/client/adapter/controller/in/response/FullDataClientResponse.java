package com.vet.hc.api.client.adapter.controller.in.response;

import com.vet.hc.api.shared.domain.query.Response;

import lombok.experimental.SuperBuilder;

/**
 * Response to get the full data of a client.
 */
@SuperBuilder
public class FullDataClientResponse extends Response<FullDataClientDto> {
}
