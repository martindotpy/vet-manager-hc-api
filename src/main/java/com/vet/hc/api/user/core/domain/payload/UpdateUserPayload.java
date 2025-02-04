package com.vet.hc.api.user.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

public interface UpdateUserPayload extends Payload {
    Long getId();

    String getFirstName();

    String getLastName();
}
