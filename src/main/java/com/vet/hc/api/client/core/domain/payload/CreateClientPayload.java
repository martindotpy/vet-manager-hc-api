package com.vet.hc.api.client.core.domain.payload;

import com.vet.hc.api.client.core.domain.enums.IdentificationType;
import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new client.
 */
public interface CreateClientPayload extends Payload {
    String getFirstName();

    String getLastName();

    String getIdentification();

    IdentificationType getIdentificationType();

    String getAddress();
}
