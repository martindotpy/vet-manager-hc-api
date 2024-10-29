package com.vet.hc.api.client.domain.model;

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
