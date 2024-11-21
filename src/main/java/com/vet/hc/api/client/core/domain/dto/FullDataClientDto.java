package com.vet.hc.api.client.core.domain.dto;

import java.util.Set;

import com.vet.hc.api.client.email.domain.dto.ClientEmailDto;
import com.vet.hc.api.client.phone.domain.dto.ClientPhoneDto;

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
