package com.vet.hc.api.client.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a client email.
 */
@Getter
@Builder
public class ClientEmail {
    private Long id;

    private String email;

    private Client client;
}
