package com.vet.hc.api.client.domain.model;

import java.util.Set;

import com.vet.hc.api.client.domain.enums.IdentificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a client.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Long id;

    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;

    private Set<ClientEmail> emails;
    private Set<ClientPhone> phones;
}
