package com.vet.hc.api.client.application.dto;

import com.vet.hc.api.client.domain.enums.IdentificationType;
import com.vet.hc.api.shared.domain.spanish.SpanishPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Client DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    @SpanishPropertyName("Id")
    private Long id;

    @SpanishPropertyName("Nombre")
    private String firstName;
    @SpanishPropertyName("Apellido")
    private String lastName;
    @SpanishPropertyName("Identificación")
    private String identification;
    @SpanishPropertyName("Tipo de identificación")
    private IdentificationType identificationType;
    @SpanishPropertyName("Dirección")
    private String address;
}
