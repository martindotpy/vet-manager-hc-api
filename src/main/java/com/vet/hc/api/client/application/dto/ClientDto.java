package com.vet.hc.api.client.application.dto;

import com.vet.hc.api.client.domain.enums.IdentificationType;

import lombok.Builder;
import lombok.Getter;

/**
 * Client DTO.
 */
@Getter
@Builder
public class ClientDto {
    private Long id;

    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}