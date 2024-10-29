package com.vet.hc.api.client.adapter.in.request;

import java.util.Set;

import com.vet.hc.api.client.application.dto.ClientDto;
import com.vet.hc.api.client.application.dto.ClientEmailDto;
import com.vet.hc.api.client.application.dto.ClientPhoneDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a request to update a data client.
 *
 * <p>
 * If a phone or email is removed from the set, it will be deleted from the
 * database.
 * </p>
 * <p>
 * If a phone or email is added to the set without an ID, it will be inserted
 * into the
 * database.
 * </p>
 * <p>
 * If a phone or email is added to the set with an ID, it will be updated in the
 * database.
 * </p>
 * <p>
 * If the client has no phones, the phones set will be empty. The same applies
 * to the emails set.
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFullDataClientRequest {
    private ClientDto client;
    private Set<ClientEmailDto> emails;
    private Set<ClientPhoneDto> phones;
}
