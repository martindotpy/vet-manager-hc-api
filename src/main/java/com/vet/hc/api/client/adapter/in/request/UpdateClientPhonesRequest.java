package com.vet.hc.api.client.adapter.in.request;

import java.util.Set;

import com.vet.hc.api.client.application.dto.ClientPhoneDto;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a request to update client phones.
 *
 * <p>
 * If a phone is removed from the set, it will be deleted from the database.
 * </p>
 * <p>
 * If a phone is added to the set without an ID, it will be inserted into the
 * database.
 * </p>
 * <p>
 * If a phone is added to the set with an ID, it will be updated in the
 * database.
 * </p>
 * <p>
 * If the client has no phones, the phones set will be empty.
 * </p>
 */
@Getter
@Builder
public class UpdateClientPhonesRequest {
    private Set<ClientPhoneDto> phones;
}
