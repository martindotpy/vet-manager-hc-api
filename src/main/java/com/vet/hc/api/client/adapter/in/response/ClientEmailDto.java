package com.vet.hc.api.client.adapter.in.response;

import lombok.Builder;
import lombok.Getter;

/**
 * Client email DTO.
 *
 * <p>This class is always assocciated with a client.</p>
 */
@Getter
@Builder
public class ClientEmailDto {
    private Long id;

    private String email;
}
