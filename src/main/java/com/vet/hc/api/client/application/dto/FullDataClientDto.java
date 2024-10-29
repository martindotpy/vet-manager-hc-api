package com.vet.hc.api.client.application.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Full data client DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullDataClientDto {
    private ClientDto client;
    private Set<ClientEmailDto> emails;
    private Set<ClientPhoneDto> phones;
}
