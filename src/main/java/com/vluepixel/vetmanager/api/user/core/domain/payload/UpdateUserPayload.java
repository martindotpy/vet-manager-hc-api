package com.vluepixel.vetmanager.api.user.core.domain.payload;

import com.vluepixel.vetmanager.api.shared.domain.payload.Payload;

public interface UpdateUserPayload extends Payload {
    Long getId();

    String getFirstName();

    String getLastName();
}
