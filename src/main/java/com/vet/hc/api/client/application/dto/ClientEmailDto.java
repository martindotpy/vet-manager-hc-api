package com.vet.hc.api.client.application.dto;

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
