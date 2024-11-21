package com.vet.hc.api.client.phone.domain.model;

import com.vet.hc.api.client.core.domain.model.Client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a client phone.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPhone {
    private Long id;

    private String phone;

    private Client client;
}
