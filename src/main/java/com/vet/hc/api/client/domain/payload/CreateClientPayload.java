package com.vet.hc.api.client.domain.payload;

import com.vet.hc.api.client.domain.enums.IdentificationType;
import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Command to create a new client.
 */
public interface CreateClientPayload extends Payload {
    String getFirstName();

    String getLastName();

    String getIdentification();

    IdentificationType getIdentificationType();

    String getAddress();
}
