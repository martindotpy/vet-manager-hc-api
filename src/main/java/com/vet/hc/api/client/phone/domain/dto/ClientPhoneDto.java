package com.vet.hc.api.client.phone.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Client phone DTO.
 *
 * <p>
 * This class is always assocciated with a client.
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPhoneDto {
    private Long id;

    private String phone;
}
