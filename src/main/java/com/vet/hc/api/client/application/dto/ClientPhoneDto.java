package com.vet.hc.api.client.application.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Client phone DTO.
 *
 * <p>This class is always assocciated with a client.</p>
 */
@Getter
@Builder
public class ClientPhoneDto {
    private Long id;

    private String phone;
}
