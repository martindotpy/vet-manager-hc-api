package com.vet.hc.api.client.email.domain.model;

import com.vet.hc.api.client.core.domain.model.Client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a client email.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEmail {
    private Long id;

    private String email;

    private Client client;
}
