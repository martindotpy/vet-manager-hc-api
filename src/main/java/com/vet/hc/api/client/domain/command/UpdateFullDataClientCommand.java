package com.vet.hc.api.client.domain.command;

import com.vet.hc.api.client.domain.enums.IdentificationType;
import com.vet.hc.api.shared.domain.command.Command;

import lombok.Builder;
import lombok.Getter;

/**
 * Command to update the data client.
 *
 * <p>
 * If a phone or email is removed from the set, it will be deleted from the
 * database.
 * </p>
 * <p>
 * If a phone or email is added to the set without an ID, it will be inserted
 * into the
 * database.
 * </p>
 * <p>
 * If a phone or email is added to the set with an ID, it will be updated in the
 * database.
 * </p>
 * <p>
 * If the client has no phones, the phones set will be empty. The same applies
 * to the emails set.
 * </p>
 */
@Getter
@Builder
public class UpdateFullDataClientCommand implements Command {
    private String firstName;
    private String lastName;
    private String identification;
    private IdentificationType identificationType;
    private String address;
}
