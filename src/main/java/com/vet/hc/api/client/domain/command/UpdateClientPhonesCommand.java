package com.vet.hc.api.client.domain.command;

import java.util.Set;

import com.vet.hc.api.client.domain.model.ClientPhone;
import com.vet.hc.api.shared.domain.command.Command;

import lombok.Builder;
import lombok.Getter;

/**
 * Command to update the client phones'.
 *
 * <p>
 * If a phone is removed from the set, it will be deleted from the database.
 * </p>
 * <p>
 * If a phone is added to the set without an ID, it will be inserted into the
 * database.
 * </p>
 * <p>
 * If a phone is added to the set with an ID, it will be updated in the
 * database.
 * </p>
 * <p>
 * If the client has no phones, the phones set will be empty.
 * </p>
 */
@Getter
@Builder
public class UpdateClientPhonesCommand implements Command {
    private Set<ClientPhone> phones;
}
