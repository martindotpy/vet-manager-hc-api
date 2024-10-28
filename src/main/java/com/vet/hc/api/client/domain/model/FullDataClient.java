package com.vet.hc.api.client.domain.model;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

/**
 * Full data of a client.
 */
@Getter
@Builder
public class FullDataClient {
    private Client client;
    private Set<ClientEmail> emails;
    private Set<ClientPhone> phones;
}
