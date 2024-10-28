package com.vet.hc.api.client.domain.command;

import com.vet.hc.api.client.domain.enums.IdentificationType;
import com.vet.hc.api.shared.domain.command.Command;

import lombok.Builder;
import lombok.Getter;

/**
 * Command to update the data client.
 */
@Getter
@Builder
public class UpdateClientCommand implements Command {
    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}
