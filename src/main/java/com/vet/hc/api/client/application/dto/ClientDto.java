package com.vet.hc.api.client.application.dto;

import com.vet.hc.api.client.domain.enums.IdentificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Client DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;

    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}
