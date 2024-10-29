package com.vet.hc.api.client.domain.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Full data of a client.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullDataClient {
    private Client client;
    private Set<ClientEmail> emails;
    private Set<ClientPhone> phones;
}
