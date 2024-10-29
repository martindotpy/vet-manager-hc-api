package com.vet.hc.api.client.domain.command;

import com.vet.hc.api.client.domain.enums.IdentificationType;
import com.vet.hc.api.shared.domain.command.Command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Command to create a new client.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientCommand implements Command {
    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}
