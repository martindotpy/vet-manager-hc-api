package com.vet.hc.api.client.domain.command;

import java.util.Set;

import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.client.domain.model.ClientEmail;
import com.vet.hc.api.client.domain.model.ClientPhone;
import com.vet.hc.api.shared.domain.command.Command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFullDataClientCommand implements Command {
    private Client client;
    private Set<ClientEmail> emails;
    private Set<ClientPhone> phones;
}
