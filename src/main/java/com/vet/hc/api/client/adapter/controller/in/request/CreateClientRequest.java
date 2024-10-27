package com.vet.hc.api.client.adapter.controller.in.request;

import com.vet.hc.api.client.domain.enums.IdentificationType;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a request to create a client.
 */
@Getter
@Builder
public class CreateClientRequest {
    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}
