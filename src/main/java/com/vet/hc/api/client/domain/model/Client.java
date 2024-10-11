package com.vet.hc.api.client.domain.model;

import com.vet.hc.api.client.domain.enums.IdentificationType;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a client.
 */
@Getter
@Builder
public class Client {
    private Long id;

    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}
