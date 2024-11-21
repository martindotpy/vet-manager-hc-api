package com.vet.hc.api.client.email.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Client email DTO.
 *
 * <p>
 * This class is always assocciated with a client.
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEmailDto {
    private Long id;

    private String email;
}
