package com.vet.hc.api.client.adapter.in.request;

import com.vet.hc.api.client.domain.enums.IdentificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a request to create a client.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequest {
    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}
