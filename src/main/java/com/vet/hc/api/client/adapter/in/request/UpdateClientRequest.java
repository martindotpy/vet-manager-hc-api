package com.vet.hc.api.client.adapter.in.request;

import com.vet.hc.api.client.domain.enums.IdentificationType;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a request to update a client.
 */
@Getter
@Builder
public class UpdateClientRequest {
    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}
