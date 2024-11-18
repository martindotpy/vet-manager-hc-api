package com.vet.hc.api.client.domain.payload;

import java.util.Set;

import com.vet.hc.api.client.domain.enums.IdentificationType;
import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to update the data client.
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
public interface UpdateFullDataClientPayload extends Payload {
    Long getId();

    String getFirstName();

    String getLastName();

    String getIdentification();

    IdentificationType getIdentificationType();

    String getAddress();

    Set<String> getEmails();

    Set<String> getPhones();
}
