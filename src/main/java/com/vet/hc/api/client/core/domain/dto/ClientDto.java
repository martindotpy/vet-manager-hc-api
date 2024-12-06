package com.vet.hc.api.client.core.domain.dto;

import com.vet.hc.api.client.core.domain.enums.IdentificationType;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Client DTO.
 */
@ColumnClassName("Cliente")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Nombre")
    private String firstName;
    @ColumnPropertyName("Apellido")
    private String lastName;
    @ColumnPropertyName("Identificación")
    private String identification;
    @ColumnPropertyName("Tipo de identificación")
    private IdentificationType identificationType;
    @ColumnPropertyName("Dirección")
    private String address;
}
