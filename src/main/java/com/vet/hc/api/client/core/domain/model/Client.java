package com.vet.hc.api.client.core.domain.model;

import java.util.Set;

import com.vet.hc.api.client.core.domain.enums.IdentificationType;
import com.vet.hc.api.client.email.domain.model.ClientEmail;
import com.vet.hc.api.client.phone.domain.model.ClientPhone;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a client.
 */
@ColumnClassName("Cliente")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
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

    @ColumnPropertyName("Emails")
    private Set<ClientEmail> emails;
    @ColumnPropertyName("Teléfonos")
    private Set<ClientPhone> phones;
}
