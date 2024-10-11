package com.vet.hc.api.client.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a client phone.
 */
@Getter
@Builder
public class ClientPhone {
    private Long id;

    private String phone;

    private Client client;
}
