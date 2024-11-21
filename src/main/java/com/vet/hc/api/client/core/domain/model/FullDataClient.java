package com.vet.hc.api.client.core.domain.model;

import java.util.Set;

import com.vet.hc.api.client.email.domain.model.ClientEmail;
import com.vet.hc.api.client.phone.domain.model.ClientPhone;

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
