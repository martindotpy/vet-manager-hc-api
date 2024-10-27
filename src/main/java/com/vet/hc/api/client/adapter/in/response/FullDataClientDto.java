package com.vet.hc.api.client.adapter.in.response;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

/**
 * Full data client DTO.
 */
@Getter
@Builder
public class FullDataClientDto {
    private ClientDto client;
    private Set<ClientEmailDto> emails;
    private Set<ClientPhoneDto> phones;
}
